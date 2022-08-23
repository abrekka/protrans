package no.ugland.utransprod.gui.manuelt;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import java.util.List;
import java.util.Set;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

import no.ugland.utransprod.ProtransModule;
import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.LFEnum;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.LoginImpl;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.edit.EditAccidentView;
import no.ugland.utransprod.gui.handlers.AccidentViewHandler;
import no.ugland.utransprod.gui.model.AccidentModel;
import no.ugland.utransprod.gui.util.JDateChooserFinder;
import no.ugland.utransprod.model.Accident;
import no.ugland.utransprod.model.AccidentParticipant;
import no.ugland.utransprod.model.ApplicationUser;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.service.AccidentManager;
import no.ugland.utransprod.service.ApplicationUserManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.enums.LazyLoadEnum;
import no.ugland.utransprod.test.ManuellTest;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Util;

import org.fest.swing.core.BasicComponentFinder;
import org.fest.swing.core.ComponentFinder;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.finder.JOptionPaneFinder;
import org.fest.swing.finder.WindowFinder;
import org.fest.swing.fixture.DialogFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.toedter.calendar.JDateChooser;

@Category(ManuellTest.class)
public class EditAccidentViewTest {
    static {
	try {

	    UIManager.setLookAndFeel(LFEnum.LNF_LIQUID.getClassName());
	    JFrame.setDefaultLookAndFeelDecorated(true);
	    // LiquidLookAndFeel.setLiquidDecorations(true, "mac");

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    private DialogFixture dialogFixture;

    private AccidentManager accidentManager;
    private ManagerRepository managerRepository;

    /**
     * @see junit.framework.TestCase#setUp()
     */

    @Before
    public void setUp() throws Exception {
	// FailOnThreadViolationRepaintManager.install();
	accidentManager = (AccidentManager) ModelUtil.getBean("accidentManager");
	Injector injector = Guice.createInjector(new ProtransModule());

	managerRepository = injector.getInstance(ManagerRepository.class);

	ApplicationUserManager applicationUserManager = (ApplicationUserManager) ModelUtil.getBean("applicationUserManager");
	ApplicationUser user;
	user = applicationUserManager.login("admin", "admin");
	applicationUserManager.lazyLoad(user, new LazyLoadEnum[][] { { LazyLoadEnum.USER_ROLES, LazyLoadEnum.NONE } });
	UserType userType = user.getUserRoles().iterator().next().getUserType();

	Login login = new LoginImpl(user, userType);

	AccidentViewHandler accidentViewHandler = new AccidentViewHandler(login, managerRepository);

	AccidentModel accidentModel = new AccidentModel(new Accident());
	final EditAccidentView editAccidentView = new EditAccidentView(false, accidentModel, accidentViewHandler);

	JDialog dialog = GuiActionRunner.execute(new GuiQuery<JDialog>() {
	    protected JDialog executeInEDT() {
		JDialog dialog = new JDialog();
		WindowInterface window = new JDialogAdapter(dialog);
		dialog.add(editAccidentView.buildPanel(window));
		dialog.pack();
		return dialog;
	    }
	});
	dialogFixture = new DialogFixture(dialog);
	dialogFixture.show();
	//

    }

    @After
    public void tearDown() throws Exception {
	dialogFixture.cleanUp();

	Accident accident = new Accident();
	accident.setRegisteredBy("test");
	List<Accident> list = accidentManager.findByObject(accident);
	if (list != null) {
	    for (Accident acc : list) {
		accidentManager.removeObject(acc);
	    }
	}
    }

    @Test
    public void testShow() {
	dialogFixture.requireVisible();
	dialogFixture.close();
    }

    @Test
    public void testInsertAccident() {
	dialogFixture.textBox("TextFieldRegisteredBy").enterText("test");

	dialogFixture.robot.finder().findByName("DateChooserRegistrationDate");

	dialogFixture.comboBox("ComboBoxJobFunction").selectItem(0);

	JDateChooser dateChooser = BasicComponentFinder.finderWithCurrentAwtHierarchy().find(new JDateChooserFinder("DateChooserAccidentDate"));
	dateChooser.setDate(Util.getCurrentDate());

	dialogFixture.textBox("TextAreaDescription").enterText("testbeskrivelse");
	dialogFixture.textBox("TextAreaCause").enterText("testårsak");

	dialogFixture.radioButton("RadioButtonPersonalInjury").click();
	dialogFixture.checkBox("CheckBoxLeader").check();
	dialogFixture.checkBox("CheckBoxPolice").check();
	dialogFixture.checkBox("CheckBoxSocialSecurity").check();

	dialogFixture.button("SaveAccident").click();

	Accident accident = new Accident();
	accident.setRegisteredBy("test");

	List<Accident> list = accidentManager.findByObject(accident);
	assertNotNull(list);
	assertEquals(1, list.size());

	accident = list.get(0);

	assertEquals("test", accident.getRegisteredBy());
	assertEquals("Transport", accident.getJobFunction().getJobFunctionName());
	assertEquals("testbeskrivelse", accident.getAccidentDescription());
	assertEquals("testårsak", accident.getAccidentCause());
	assertEquals(Integer.valueOf(1), accident.getPersonalInjuryOver24());
	assertEquals(Integer.valueOf(1), accident.getReportedLeader());
	assertEquals(Integer.valueOf(1), accident.getReportedPolice());
	assertEquals(Integer.valueOf(1), accident.getReportedSocialSecurity());
	dialogFixture.close();
    }

    @Test
    public void testAddParticipant() {
	dialogFixture.textBox("TextFieldRegisteredBy").enterText("test");
	dialogFixture.comboBox("ComboBoxJobFunction").selectItem(0);

	JDateChooser dateChooser = dialogFixture.robot.finder().find(new JDateChooserFinder("DateChooserAccidentDate"));
	dateChooser.setDate(Util.getCurrentDate());
	dialogFixture.radioButton("RadioButtonPersonalInjury").click();
	dialogFixture.textBox("TextAreaDescription").enterText("testbeskrivelse");
	dialogFixture.textBox("TextAreaCause").enterText("testårsak");

	dialogFixture.button("ButtonAddParticipant").click();

	DialogFixture participantDialog = WindowFinder.findDialog("EditAccidentParticipantView").using(dialogFixture.robot);

	participantDialog.textBox("TextFieldFirstName").enterText("Atle");
	participantDialog.textBox("TextFieldLastName").enterText("Brekka");
	participantDialog.comboBox("ComboBoxEmployeeType").selectItem(1);
	participantDialog.button("ButtonOk").click();

	dialogFixture.list("ListParticipant").selectItem(0);

	dialogFixture.button("SaveAccident").click();

	Accident accident = new Accident();
	accident.setRegisteredBy("test");

	List<Accident> list = accidentManager.findByObject(accident);
	assertNotNull(list);
	assertEquals(1, list.size());

	accident = list.get(0);

	accidentManager.lazyLoad(accident, new LazyLoadEnum[][] { { LazyLoadEnum.ACCIDENT_PARTICIPANTS, LazyLoadEnum.NONE } });

	assertEquals("test", accident.getRegisteredBy());
	assertEquals("Transport", accident.getJobFunction().getJobFunctionName());
	assertEquals("testbeskrivelse", accident.getAccidentDescription());
	assertEquals("testårsak", accident.getAccidentCause());

	Set<AccidentParticipant> participants = accident.getAccidentParticipants();
	assertNotNull(participants);
	assertEquals(1, participants.size());

	AccidentParticipant participant = participants.iterator().next();
	assertEquals("Atle", participant.getFirstName());
	assertEquals("Brekka", participant.getLastName());
	assertEquals("Sjåfør", participant.getEmployeeType().getEmployeeTypeName());
	dialogFixture.close();
    }

    @Test
    public void testDeleteParticipant() {
	dialogFixture.textBox("TextFieldRegisteredBy").enterText("test");
	dialogFixture.comboBox("ComboBoxJobFunction").selectItem(0);

	JDateChooser dateChooser = dialogFixture.robot.finder().find(new JDateChooserFinder("DateChooserAccidentDate"));
	dateChooser.setDate(Util.getCurrentDate());
	dialogFixture.radioButton("RadioButtonPersonalInjury").click();
	dialogFixture.textBox("TextAreaDescription").enterText("testbeskrivelse");
	dialogFixture.textBox("TextAreaCause").enterText("testårsak");

	dialogFixture.button("ButtonDeleteParticipant").requireDisabled();

	dialogFixture.button("ButtonAddParticipant").click();

	DialogFixture participantDialog = WindowFinder.findDialog("EditAccidentParticipantView").using(dialogFixture.robot);

	participantDialog.textBox("TextFieldFirstName").enterText("Atle");
	participantDialog.textBox("TextFieldLastName").enterText("Brekka");
	participantDialog.comboBox("ComboBoxEmployeeType").selectItem(1);
	participantDialog.button("ButtonOk").click();

	dialogFixture.list("ListParticipant").selectItem(0);

	dialogFixture.button("SaveAccident").click();

	Accident accident = new Accident();
	accident.setRegisteredBy("test");

	List<Accident> list = accidentManager.findByObject(accident);
	assertNotNull(list);
	assertEquals(1, list.size());

	accident = list.get(0);

	accidentManager.lazyLoad(accident, new LazyLoadEnum[][] { { LazyLoadEnum.ACCIDENT_PARTICIPANTS, LazyLoadEnum.NONE } });

	assertEquals("test", accident.getRegisteredBy());
	assertEquals("Transport", accident.getJobFunction().getJobFunctionName());
	assertEquals("testbeskrivelse", accident.getAccidentDescription());
	assertEquals("testårsak", accident.getAccidentCause());

	Set<AccidentParticipant> participants = accident.getAccidentParticipants();
	assertNotNull(participants);
	assertEquals(1, participants.size());

	AccidentParticipant participant = participants.iterator().next();
	assertEquals("Atle", participant.getFirstName());
	assertEquals("Brekka", participant.getLastName());
	assertEquals("Sjåfør", participant.getEmployeeType().getEmployeeTypeName());

	dialogFixture.button("ButtonDeleteParticipant").requireEnabled();
	dialogFixture.button("ButtonDeleteParticipant").click();

	JOptionPaneFinder.findOptionPane().using(dialogFixture.robot).buttonWithText("Ja").click();

	assertEquals(0, dialogFixture.list("ListParticipant").target.getModel().getSize());

	dialogFixture.button("SaveAccident").click();

	accident = new Accident();
	accident.setRegisteredBy("test");

	list = accidentManager.findByObject(accident);
	assertNotNull(list);
	assertEquals(1, list.size());

	accident = list.get(0);

	accidentManager.lazyLoad(accident, new LazyLoadEnum[][] { { LazyLoadEnum.ACCIDENT_PARTICIPANTS, LazyLoadEnum.NONE } });

	participants = accident.getAccidentParticipants();
	assertNotNull(participants);
	assertEquals(0, participants.size());
	dialogFixture.close();

    }

    @Test
    public void testPrint() {
	dialogFixture.textBox("TextFieldRegisteredBy").enterText("test");

	dialogFixture.robot.finder().findByName("DateChooserRegistrationDate");

	dialogFixture.comboBox("ComboBoxJobFunction").selectItem(0);

	JDateChooser dateChooser = BasicComponentFinder.finderWithCurrentAwtHierarchy().find(new JDateChooserFinder("DateChooserAccidentDate"));
	dateChooser.setDate(Util.getCurrentDate());

	dialogFixture.textBox("TextAreaDescription").enterText("testbeskrivelse");
	dialogFixture.textBox("TextAreaCause").enterText("testårsak");

	dialogFixture.radioButton("RadioButtonPersonalInjury").click();
	dialogFixture.checkBox("CheckBoxLeader").check();
	dialogFixture.checkBox("CheckBoxPolice").check();
	dialogFixture.checkBox("CheckBoxSocialSecurity").check();

	dialogFixture.button("SaveAccident").click();

	dialogFixture.button("ButtonPrint").click();

	DialogFixture printer = WindowFinder.findDialog("Hendelse/ulykke").withTimeout(20000).using(dialogFixture.robot);
	printer.button("ButtonCancel").click();
	dialogFixture.close();
    }

    @Test
    public void testCancelAddParticipant() {
	dialogFixture.textBox("TextFieldRegisteredBy").enterText("test");
	dialogFixture.comboBox("ComboBoxJobFunction").selectItem(0);

	JDateChooser dateChooser = dialogFixture.robot.finder().find(new JDateChooserFinder("DateChooserAccidentDate"));
	dateChooser.setDate(Util.getCurrentDate());
	dialogFixture.radioButton("RadioButtonPersonalInjury").click();
	dialogFixture.textBox("TextAreaDescription").enterText("testbeskrivelse");
	dialogFixture.textBox("TextAreaCause").enterText("testårsak");

	dialogFixture.button("ButtonAddParticipant").click();

	DialogFixture participantDialog = WindowFinder.findDialog("EditAccidentParticipantView").using(dialogFixture.robot);

	participantDialog.button("EditCancelAccidentParticipant").click();

	assertEquals(0, dialogFixture.list("ListParticipant").target.getModel().getSize());

	dialogFixture.button("SaveAccident").click();

	Accident accident = new Accident();
	accident.setRegisteredBy("test");

	List<Accident> list = accidentManager.findByObject(accident);
	assertNotNull(list);
	assertEquals(1, list.size());

	accident = list.get(0);

	accidentManager.lazyLoad(accident, new LazyLoadEnum[][] { { LazyLoadEnum.ACCIDENT_PARTICIPANTS, LazyLoadEnum.NONE } });

	assertEquals("test", accident.getRegisteredBy());
	assertEquals("Transport", accident.getJobFunction().getJobFunctionName());
	assertEquals("testbeskrivelse", accident.getAccidentDescription());
	assertEquals("testårsak", accident.getAccidentCause());

	Set<AccidentParticipant> participants = accident.getAccidentParticipants();
	assertNotNull(participants);
	assertEquals(0, participants.size());
	dialogFixture.close();

    }

    @Test
    public void testSetPreventiveActionCommentAndResponsible() {

	dialogFixture.textBox("TextAreaPreventiveActionComment").enterText("tiltaksbeskrivelse");

	dialogFixture.textBox("TextFieldRegisteredBy").enterText("test");

	ComponentFinder finder = dialogFixture.robot.finder();
	dialogFixture.robot.finder().findByName("DateChooserRegistrationDate");

	dialogFixture.comboBox("ComboBoxJobFunction").selectItem(0);

	JDateChooser dateChooser = finder.find(new JDateChooserFinder("DateChooserAccidentDate"));
	dateChooser.setDate(Util.getCurrentDate());

	dialogFixture.textBox("TextAreaDescription").enterText("testbeskrivelse");
	dialogFixture.textBox("TextAreaCause").enterText("testårsak");

	dialogFixture.radioButton("RadioButtonPersonalInjury").click();
	dialogFixture.checkBox("CheckBoxLeader").check();
	dialogFixture.checkBox("CheckBoxPolice").check();
	dialogFixture.checkBox("CheckBoxSocialSecurity").check();

	dialogFixture.button("SaveAccident").click();

	Accident accident = new Accident();
	accident.setRegisteredBy("test");

	List<Accident> list = accidentManager.findByObject(accident);
	assertNotNull(list);
	assertEquals(1, list.size());

	accident = list.get(0);

	assertEquals("test", accident.getRegisteredBy());
	assertEquals("Transport", accident.getJobFunction().getJobFunctionName());
	assertEquals("tiltaksbeskrivelse", accident.getPreventiveActionComment());
	assertEquals("testbeskrivelse", accident.getAccidentDescription());
	assertEquals("testårsak", accident.getAccidentCause());
	assertEquals(Integer.valueOf(1), accident.getPersonalInjuryOver24());
	assertEquals(Integer.valueOf(1), accident.getReportedLeader());
	assertEquals(Integer.valueOf(1), accident.getReportedPolice());
	assertEquals(Integer.valueOf(1), accident.getReportedSocialSecurity());
	dialogFixture.close();
    }
}
