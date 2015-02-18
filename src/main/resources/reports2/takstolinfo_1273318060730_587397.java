/*
 * Generated by JasperReports - 08.05.10 13:27
 */
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.fill.*;

import java.util.*;
import java.math.*;
import java.text.*;
import java.io.*;
import java.net.*;

import net.sf.jasperreports.engine.*;
import java.util.*;
import net.sf.jasperreports.engine.data.*;


/**
 *
 */
public class takstolinfo_1273318060730_587397 extends JREvaluator
{


    /**
     *
     */
    private JRFillParameter parameter_REPORT_LOCALE = null;
    private JRFillParameter parameter_REPORT_TIME_ZONE = null;
    private JRFillParameter parameter_REPORT_VIRTUALIZER = null;
    private JRFillParameter parameter_REPORT_FILE_RESOLVER = null;
    private JRFillParameter parameter_REPORT_SCRIPTLET = null;
    private JRFillParameter parameter_REPORT_PARAMETERS_MAP = null;
    private JRFillParameter parameter_REPORT_CONNECTION = null;
    private JRFillParameter parameter_REPORT_CLASS_LOADER = null;
    private JRFillParameter parameter_REPORT_DATA_SOURCE = null;
    private JRFillParameter parameter_REPORT_URL_HANDLER_FACTORY = null;
    private JRFillParameter parameter_IS_IGNORE_PAGINATION = null;
    private JRFillParameter parameter_REPORT_FORMAT_FACTORY = null;
    private JRFillParameter parameter_REPORT_MAX_COUNT = null;
    private JRFillParameter parameter_utstikk_bilde = null;
    private JRFillParameter parameter_REPORT_TEMPLATES = null;
    private JRFillParameter parameter_ugland_logo = null;
    private JRFillParameter parameter_REPORT_RESOURCE_BUNDLE = null;
    private JRFillField field_leveringsadresse = null;
    private JRFillField field_vinkel = null;
    private JRFillField field_trossDrawer = null;
    private JRFillField field_spennvidde = null;
    private JRFillField field_svilleklaring = null;
    private JRFillField field_egenvekt = null;
    private JRFillField field_kundenr = null;
    private JRFillField field_ordernr = null;
    private JRFillField field_kode = null;
    private JRFillField field_hoydeOverHavet = null;
    private JRFillField field_snolast = null;
    private JRFillField field_oensketUke = null;
    private JRFillField field_baeringGulv = null;
    private JRFillField field_virkesbredde = null;
    private JRFillField field_kundeRef = null;
    private JRFillField field_postnr = null;
    private JRFillField field_navn = null;
    private JRFillField field_utstikkType = null;
    private JRFillField field_maksHoyde = null;
    private JRFillField field_antall = null;
    private JRFillField field_utstikkslengde = null;
    private JRFillField field_tlfByggeplass = null;
    private JRFillField field_beregnetTid = null;
    private JRFillField field_poststed = null;
    private JRFillField field_nedstikk = null;
    private JRFillField field_isolasjonshoyde = null;
    private JRFillField field_beregnetFor = null;
    private JRFillField field_takstoltype = null;
    private JRFillField field_beskrivelse = null;
    private JRFillField field_loddkutt = null;
    private JRFillField field_tlfKunde = null;
    private JRFillField field_rombreddeAStol = null;
    private JRFillField field_prodno = null;
    private JRFillVariable variable_PAGE_NUMBER = null;
    private JRFillVariable variable_COLUMN_NUMBER = null;
    private JRFillVariable variable_REPORT_COUNT = null;
    private JRFillVariable variable_PAGE_COUNT = null;
    private JRFillVariable variable_COLUMN_COUNT = null;
    private JRFillVariable variable_SUM_beregnetTid_1 = null;


    /**
     *
     */
    public void customizedInit(
        Map pm,
        Map fm,
        Map vm
        )
    {
        initParams(pm);
        initFields(fm);
        initVars(vm);
    }


    /**
     *
     */
    private void initParams(Map pm)
    {
        parameter_REPORT_LOCALE = (JRFillParameter)pm.get("REPORT_LOCALE");
        parameter_REPORT_TIME_ZONE = (JRFillParameter)pm.get("REPORT_TIME_ZONE");
        parameter_REPORT_VIRTUALIZER = (JRFillParameter)pm.get("REPORT_VIRTUALIZER");
        parameter_REPORT_FILE_RESOLVER = (JRFillParameter)pm.get("REPORT_FILE_RESOLVER");
        parameter_REPORT_SCRIPTLET = (JRFillParameter)pm.get("REPORT_SCRIPTLET");
        parameter_REPORT_PARAMETERS_MAP = (JRFillParameter)pm.get("REPORT_PARAMETERS_MAP");
        parameter_REPORT_CONNECTION = (JRFillParameter)pm.get("REPORT_CONNECTION");
        parameter_REPORT_CLASS_LOADER = (JRFillParameter)pm.get("REPORT_CLASS_LOADER");
        parameter_REPORT_DATA_SOURCE = (JRFillParameter)pm.get("REPORT_DATA_SOURCE");
        parameter_REPORT_URL_HANDLER_FACTORY = (JRFillParameter)pm.get("REPORT_URL_HANDLER_FACTORY");
        parameter_IS_IGNORE_PAGINATION = (JRFillParameter)pm.get("IS_IGNORE_PAGINATION");
        parameter_REPORT_FORMAT_FACTORY = (JRFillParameter)pm.get("REPORT_FORMAT_FACTORY");
        parameter_REPORT_MAX_COUNT = (JRFillParameter)pm.get("REPORT_MAX_COUNT");
        parameter_utstikk_bilde = (JRFillParameter)pm.get("utstikk_bilde");
        parameter_REPORT_TEMPLATES = (JRFillParameter)pm.get("REPORT_TEMPLATES");
        parameter_ugland_logo = (JRFillParameter)pm.get("ugland_logo");
        parameter_REPORT_RESOURCE_BUNDLE = (JRFillParameter)pm.get("REPORT_RESOURCE_BUNDLE");
    }


    /**
     *
     */
    private void initFields(Map fm)
    {
        field_leveringsadresse = (JRFillField)fm.get("leveringsadresse");
        field_vinkel = (JRFillField)fm.get("vinkel");
        field_trossDrawer = (JRFillField)fm.get("trossDrawer");
        field_spennvidde = (JRFillField)fm.get("spennvidde");
        field_svilleklaring = (JRFillField)fm.get("svilleklaring");
        field_egenvekt = (JRFillField)fm.get("egenvekt");
        field_kundenr = (JRFillField)fm.get("kundenr");
        field_ordernr = (JRFillField)fm.get("ordernr");
        field_kode = (JRFillField)fm.get("kode");
        field_hoydeOverHavet = (JRFillField)fm.get("hoydeOverHavet");
        field_snolast = (JRFillField)fm.get("snolast");
        field_oensketUke = (JRFillField)fm.get("oensketUke");
        field_baeringGulv = (JRFillField)fm.get("baeringGulv");
        field_virkesbredde = (JRFillField)fm.get("virkesbredde");
        field_kundeRef = (JRFillField)fm.get("kundeRef");
        field_postnr = (JRFillField)fm.get("postnr");
        field_navn = (JRFillField)fm.get("navn");
        field_utstikkType = (JRFillField)fm.get("utstikkType");
        field_maksHoyde = (JRFillField)fm.get("maksHoyde");
        field_antall = (JRFillField)fm.get("antall");
        field_utstikkslengde = (JRFillField)fm.get("utstikkslengde");
        field_tlfByggeplass = (JRFillField)fm.get("tlfByggeplass");
        field_beregnetTid = (JRFillField)fm.get("beregnetTid");
        field_poststed = (JRFillField)fm.get("poststed");
        field_nedstikk = (JRFillField)fm.get("nedstikk");
        field_isolasjonshoyde = (JRFillField)fm.get("isolasjonshoyde");
        field_beregnetFor = (JRFillField)fm.get("beregnetFor");
        field_takstoltype = (JRFillField)fm.get("takstoltype");
        field_beskrivelse = (JRFillField)fm.get("beskrivelse");
        field_loddkutt = (JRFillField)fm.get("loddkutt");
        field_tlfKunde = (JRFillField)fm.get("tlfKunde");
        field_rombreddeAStol = (JRFillField)fm.get("rombreddeAStol");
        field_prodno = (JRFillField)fm.get("prodno");
    }


    /**
     *
     */
    private void initVars(Map vm)
    {
        variable_PAGE_NUMBER = (JRFillVariable)vm.get("PAGE_NUMBER");
        variable_COLUMN_NUMBER = (JRFillVariable)vm.get("COLUMN_NUMBER");
        variable_REPORT_COUNT = (JRFillVariable)vm.get("REPORT_COUNT");
        variable_PAGE_COUNT = (JRFillVariable)vm.get("PAGE_COUNT");
        variable_COLUMN_COUNT = (JRFillVariable)vm.get("COLUMN_COUNT");
        variable_SUM_beregnetTid_1 = (JRFillVariable)vm.get("SUM_beregnetTid_1");
    }


    /**
     *
     */
    public Object evaluate(int id) throws Throwable
    {
        Object value = null;

        switch (id)
        {
            case 0 : 
            {
                value = (java.lang.Integer)(new Integer(1));//$JR_EXPR_ID=0$
                break;
            }
            case 1 : 
            {
                value = (java.lang.Integer)(new Integer(1));//$JR_EXPR_ID=1$
                break;
            }
            case 2 : 
            {
                value = (java.lang.Integer)(new Integer(1));//$JR_EXPR_ID=2$
                break;
            }
            case 3 : 
            {
                value = (java.lang.Integer)(new Integer(0));//$JR_EXPR_ID=3$
                break;
            }
            case 4 : 
            {
                value = (java.lang.Integer)(new Integer(1));//$JR_EXPR_ID=4$
                break;
            }
            case 5 : 
            {
                value = (java.lang.Integer)(new Integer(0));//$JR_EXPR_ID=5$
                break;
            }
            case 6 : 
            {
                value = (java.lang.Integer)(new Integer(1));//$JR_EXPR_ID=6$
                break;
            }
            case 7 : 
            {
                value = (java.lang.Integer)(new Integer(0));//$JR_EXPR_ID=7$
                break;
            }
            case 8 : 
            {
                value = (java.math.BigDecimal)(((java.math.BigDecimal)field_beregnetTid.getValue()));//$JR_EXPR_ID=8$
                break;
            }
            case 9 : 
            {
                value = (java.lang.String)("Takstolinfo - Ordre " + ((java.lang.String)field_ordernr.getValue()));//$JR_EXPR_ID=9$
                break;
            }
            case 10 : 
            {
                value = (java.io.InputStream)(((java.io.InputStream)parameter_ugland_logo.getValue()));//$JR_EXPR_ID=10$
                break;
            }
            case 11 : 
            {
                value = (java.lang.String)("�nsket uke " + ((java.lang.Integer)field_oensketUke.getValue()));//$JR_EXPR_ID=11$
                break;
            }
            case 12 : 
            {
                value = (java.lang.String)(((java.lang.String)field_ordernr.getValue()));//$JR_EXPR_ID=12$
                break;
            }
            case 13 : 
            {
                value = (java.lang.Integer)(((java.lang.Integer)field_kundenr.getValue()));//$JR_EXPR_ID=13$
                break;
            }
            case 14 : 
            {
                value = (java.lang.String)(((java.lang.String)field_navn.getValue()));//$JR_EXPR_ID=14$
                break;
            }
            case 15 : 
            {
                value = (java.lang.String)(((java.lang.String)field_leveringsadresse.getValue()));//$JR_EXPR_ID=15$
                break;
            }
            case 16 : 
            {
                value = (java.lang.String)(((java.lang.String)field_postnr.getValue())+" "+((java.lang.String)field_poststed.getValue()));//$JR_EXPR_ID=16$
                break;
            }
            case 17 : 
            {
                value = (java.lang.Boolean)(Boolean.valueOf(((java.lang.Integer)field_hoydeOverHavet.getValue()).intValue()>0));//$JR_EXPR_ID=17$
                break;
            }
            case 18 : 
            {
                value = (java.lang.Integer)(((java.lang.Integer)field_hoydeOverHavet.getValue()));//$JR_EXPR_ID=18$
                break;
            }
            case 19 : 
            {
                value = (java.lang.String)(((java.lang.String)field_beregnetFor.getValue()));//$JR_EXPR_ID=19$
                break;
            }
            case 20 : 
            {
                value = (java.lang.Boolean)(Boolean.valueOf(((java.lang.Integer)field_snolast.getValue()).intValue()>0));//$JR_EXPR_ID=20$
                break;
            }
            case 21 : 
            {
                value = (java.lang.Integer)(((java.lang.Integer)field_snolast.getValue()));//$JR_EXPR_ID=21$
                break;
            }
            case 22 : 
            {
                value = (java.lang.Boolean)(Boolean.valueOf(((java.lang.Integer)field_egenvekt.getValue()).intValue()>0));//$JR_EXPR_ID=22$
                break;
            }
            case 23 : 
            {
                value = (java.lang.Integer)(((java.lang.Integer)field_egenvekt.getValue()));//$JR_EXPR_ID=23$
                break;
            }
            case 24 : 
            {
                value = (java.lang.Integer)(((java.lang.Integer)field_utstikkType.getValue()));//$JR_EXPR_ID=24$
                break;
            }
            case 25 : 
            {
                value = (java.io.InputStream)(((java.io.InputStream)parameter_utstikk_bilde.getValue()));//$JR_EXPR_ID=25$
                break;
            }
            case 26 : 
            {
                value = (java.lang.String)(((java.lang.String)field_tlfKunde.getValue()));//$JR_EXPR_ID=26$
                break;
            }
            case 27 : 
            {
                value = (java.lang.String)(((java.lang.String)field_tlfByggeplass.getValue()));//$JR_EXPR_ID=27$
                break;
            }
            case 28 : 
            {
                value = (java.lang.String)(((java.lang.String)field_kundeRef.getValue()));//$JR_EXPR_ID=28$
                break;
            }
            case 29 : 
            {
                value = (java.lang.String)(((java.lang.String)field_trossDrawer.getValue()));//$JR_EXPR_ID=29$
                break;
            }
            case 30 : 
            {
                value = (java.lang.Integer)(((java.lang.Integer)field_maksHoyde.getValue()));//$JR_EXPR_ID=30$
                break;
            }
            case 31 : 
            {
                value = (java.lang.String)(((java.lang.String)field_kode.getValue()));//$JR_EXPR_ID=31$
                break;
            }
            case 32 : 
            {
                value = (java.math.BigDecimal)(((java.math.BigDecimal)field_antall.getValue()));//$JR_EXPR_ID=32$
                break;
            }
            case 33 : 
            {
                value = (java.lang.String)(((java.lang.String)field_prodno.getValue()));//$JR_EXPR_ID=33$
                break;
            }
            case 34 : 
            {
                value = (java.lang.String)(((java.lang.String)field_beskrivelse.getValue()));//$JR_EXPR_ID=34$
                break;
            }
            case 35 : 
            {
                value = (java.lang.String)(((java.lang.String)field_takstoltype.getValue()));//$JR_EXPR_ID=35$
                break;
            }
            case 36 : 
            {
                value = (java.lang.Boolean)(Boolean.valueOf(((java.math.BigDecimal)field_virkesbredde.getValue()).intValue()>0));//$JR_EXPR_ID=36$
                break;
            }
            case 37 : 
            {
                value = (java.math.BigDecimal)(((java.math.BigDecimal)field_virkesbredde.getValue()));//$JR_EXPR_ID=37$
                break;
            }
            case 38 : 
            {
                value = (java.lang.Boolean)(Boolean.valueOf(((java.lang.Integer)field_utstikkslengde.getValue()).intValue()>0));//$JR_EXPR_ID=38$
                break;
            }
            case 39 : 
            {
                value = (java.lang.Integer)(((java.lang.Integer)field_utstikkslengde.getValue()));//$JR_EXPR_ID=39$
                break;
            }
            case 40 : 
            {
                value = (java.lang.Boolean)(Boolean.valueOf(((java.math.BigDecimal)field_nedstikk.getValue()).intValue()>0));//$JR_EXPR_ID=40$
                break;
            }
            case 41 : 
            {
                value = (java.math.BigDecimal)(((java.math.BigDecimal)field_nedstikk.getValue()));//$JR_EXPR_ID=41$
                break;
            }
            case 42 : 
            {
                value = (java.math.BigDecimal)(((java.math.BigDecimal)field_beregnetTid.getValue()));//$JR_EXPR_ID=42$
                break;
            }
            case 43 : 
            {
                value = (java.lang.Boolean)(Boolean.valueOf(((java.math.BigDecimal)field_spennvidde.getValue()).intValue()>0));//$JR_EXPR_ID=43$
                break;
            }
            case 44 : 
            {
                value = (java.math.BigDecimal)(((java.math.BigDecimal)field_spennvidde.getValue()));//$JR_EXPR_ID=44$
                break;
            }
            case 45 : 
            {
                value = (java.math.BigDecimal)(((java.math.BigDecimal)field_vinkel.getValue()));//$JR_EXPR_ID=45$
                break;
            }
            case 46 : 
            {
                value = (java.lang.String)(String.valueOf("Side ") + String.valueOf(((java.lang.Integer)variable_PAGE_NUMBER.getValue())) +  String.valueOf(" av "));//$JR_EXPR_ID=46$
                break;
            }
            case 47 : 
            {
                value = (java.lang.Integer)(((java.lang.Integer)variable_PAGE_NUMBER.getValue()));//$JR_EXPR_ID=47$
                break;
            }
            case 48 : 
            {
                value = (java.math.BigDecimal)(((java.math.BigDecimal)variable_SUM_beregnetTid_1.getValue()));//$JR_EXPR_ID=48$
                break;
            }
            case 49 : 
            {
                value = (java.lang.String)(String.valueOf("Side ") + String.valueOf(((java.lang.Integer)variable_PAGE_NUMBER.getValue())) +  String.valueOf(" av "));//$JR_EXPR_ID=49$
                break;
            }
            case 50 : 
            {
                value = (java.lang.Integer)(((java.lang.Integer)variable_PAGE_NUMBER.getValue()));//$JR_EXPR_ID=50$
                break;
            }
           default :
           {
           }
        }
        
        return value;
    }


    /**
     *
     */
    public Object evaluateOld(int id) throws Throwable
    {
        Object value = null;

        switch (id)
        {
            case 0 : 
            {
                value = (java.lang.Integer)(new Integer(1));//$JR_EXPR_ID=0$
                break;
            }
            case 1 : 
            {
                value = (java.lang.Integer)(new Integer(1));//$JR_EXPR_ID=1$
                break;
            }
            case 2 : 
            {
                value = (java.lang.Integer)(new Integer(1));//$JR_EXPR_ID=2$
                break;
            }
            case 3 : 
            {
                value = (java.lang.Integer)(new Integer(0));//$JR_EXPR_ID=3$
                break;
            }
            case 4 : 
            {
                value = (java.lang.Integer)(new Integer(1));//$JR_EXPR_ID=4$
                break;
            }
            case 5 : 
            {
                value = (java.lang.Integer)(new Integer(0));//$JR_EXPR_ID=5$
                break;
            }
            case 6 : 
            {
                value = (java.lang.Integer)(new Integer(1));//$JR_EXPR_ID=6$
                break;
            }
            case 7 : 
            {
                value = (java.lang.Integer)(new Integer(0));//$JR_EXPR_ID=7$
                break;
            }
            case 8 : 
            {
                value = (java.math.BigDecimal)(((java.math.BigDecimal)field_beregnetTid.getOldValue()));//$JR_EXPR_ID=8$
                break;
            }
            case 9 : 
            {
                value = (java.lang.String)("Takstolinfo - Ordre " + ((java.lang.String)field_ordernr.getOldValue()));//$JR_EXPR_ID=9$
                break;
            }
            case 10 : 
            {
                value = (java.io.InputStream)(((java.io.InputStream)parameter_ugland_logo.getValue()));//$JR_EXPR_ID=10$
                break;
            }
            case 11 : 
            {
                value = (java.lang.String)("�nsket uke " + ((java.lang.Integer)field_oensketUke.getOldValue()));//$JR_EXPR_ID=11$
                break;
            }
            case 12 : 
            {
                value = (java.lang.String)(((java.lang.String)field_ordernr.getOldValue()));//$JR_EXPR_ID=12$
                break;
            }
            case 13 : 
            {
                value = (java.lang.Integer)(((java.lang.Integer)field_kundenr.getOldValue()));//$JR_EXPR_ID=13$
                break;
            }
            case 14 : 
            {
                value = (java.lang.String)(((java.lang.String)field_navn.getOldValue()));//$JR_EXPR_ID=14$
                break;
            }
            case 15 : 
            {
                value = (java.lang.String)(((java.lang.String)field_leveringsadresse.getOldValue()));//$JR_EXPR_ID=15$
                break;
            }
            case 16 : 
            {
                value = (java.lang.String)(((java.lang.String)field_postnr.getOldValue())+" "+((java.lang.String)field_poststed.getOldValue()));//$JR_EXPR_ID=16$
                break;
            }
            case 17 : 
            {
                value = (java.lang.Boolean)(Boolean.valueOf(((java.lang.Integer)field_hoydeOverHavet.getOldValue()).intValue()>0));//$JR_EXPR_ID=17$
                break;
            }
            case 18 : 
            {
                value = (java.lang.Integer)(((java.lang.Integer)field_hoydeOverHavet.getOldValue()));//$JR_EXPR_ID=18$
                break;
            }
            case 19 : 
            {
                value = (java.lang.String)(((java.lang.String)field_beregnetFor.getOldValue()));//$JR_EXPR_ID=19$
                break;
            }
            case 20 : 
            {
                value = (java.lang.Boolean)(Boolean.valueOf(((java.lang.Integer)field_snolast.getOldValue()).intValue()>0));//$JR_EXPR_ID=20$
                break;
            }
            case 21 : 
            {
                value = (java.lang.Integer)(((java.lang.Integer)field_snolast.getOldValue()));//$JR_EXPR_ID=21$
                break;
            }
            case 22 : 
            {
                value = (java.lang.Boolean)(Boolean.valueOf(((java.lang.Integer)field_egenvekt.getOldValue()).intValue()>0));//$JR_EXPR_ID=22$
                break;
            }
            case 23 : 
            {
                value = (java.lang.Integer)(((java.lang.Integer)field_egenvekt.getOldValue()));//$JR_EXPR_ID=23$
                break;
            }
            case 24 : 
            {
                value = (java.lang.Integer)(((java.lang.Integer)field_utstikkType.getOldValue()));//$JR_EXPR_ID=24$
                break;
            }
            case 25 : 
            {
                value = (java.io.InputStream)(((java.io.InputStream)parameter_utstikk_bilde.getValue()));//$JR_EXPR_ID=25$
                break;
            }
            case 26 : 
            {
                value = (java.lang.String)(((java.lang.String)field_tlfKunde.getOldValue()));//$JR_EXPR_ID=26$
                break;
            }
            case 27 : 
            {
                value = (java.lang.String)(((java.lang.String)field_tlfByggeplass.getOldValue()));//$JR_EXPR_ID=27$
                break;
            }
            case 28 : 
            {
                value = (java.lang.String)(((java.lang.String)field_kundeRef.getOldValue()));//$JR_EXPR_ID=28$
                break;
            }
            case 29 : 
            {
                value = (java.lang.String)(((java.lang.String)field_trossDrawer.getOldValue()));//$JR_EXPR_ID=29$
                break;
            }
            case 30 : 
            {
                value = (java.lang.Integer)(((java.lang.Integer)field_maksHoyde.getOldValue()));//$JR_EXPR_ID=30$
                break;
            }
            case 31 : 
            {
                value = (java.lang.String)(((java.lang.String)field_kode.getOldValue()));//$JR_EXPR_ID=31$
                break;
            }
            case 32 : 
            {
                value = (java.math.BigDecimal)(((java.math.BigDecimal)field_antall.getOldValue()));//$JR_EXPR_ID=32$
                break;
            }
            case 33 : 
            {
                value = (java.lang.String)(((java.lang.String)field_prodno.getOldValue()));//$JR_EXPR_ID=33$
                break;
            }
            case 34 : 
            {
                value = (java.lang.String)(((java.lang.String)field_beskrivelse.getOldValue()));//$JR_EXPR_ID=34$
                break;
            }
            case 35 : 
            {
                value = (java.lang.String)(((java.lang.String)field_takstoltype.getOldValue()));//$JR_EXPR_ID=35$
                break;
            }
            case 36 : 
            {
                value = (java.lang.Boolean)(Boolean.valueOf(((java.math.BigDecimal)field_virkesbredde.getOldValue()).intValue()>0));//$JR_EXPR_ID=36$
                break;
            }
            case 37 : 
            {
                value = (java.math.BigDecimal)(((java.math.BigDecimal)field_virkesbredde.getOldValue()));//$JR_EXPR_ID=37$
                break;
            }
            case 38 : 
            {
                value = (java.lang.Boolean)(Boolean.valueOf(((java.lang.Integer)field_utstikkslengde.getOldValue()).intValue()>0));//$JR_EXPR_ID=38$
                break;
            }
            case 39 : 
            {
                value = (java.lang.Integer)(((java.lang.Integer)field_utstikkslengde.getOldValue()));//$JR_EXPR_ID=39$
                break;
            }
            case 40 : 
            {
                value = (java.lang.Boolean)(Boolean.valueOf(((java.math.BigDecimal)field_nedstikk.getOldValue()).intValue()>0));//$JR_EXPR_ID=40$
                break;
            }
            case 41 : 
            {
                value = (java.math.BigDecimal)(((java.math.BigDecimal)field_nedstikk.getOldValue()));//$JR_EXPR_ID=41$
                break;
            }
            case 42 : 
            {
                value = (java.math.BigDecimal)(((java.math.BigDecimal)field_beregnetTid.getOldValue()));//$JR_EXPR_ID=42$
                break;
            }
            case 43 : 
            {
                value = (java.lang.Boolean)(Boolean.valueOf(((java.math.BigDecimal)field_spennvidde.getOldValue()).intValue()>0));//$JR_EXPR_ID=43$
                break;
            }
            case 44 : 
            {
                value = (java.math.BigDecimal)(((java.math.BigDecimal)field_spennvidde.getOldValue()));//$JR_EXPR_ID=44$
                break;
            }
            case 45 : 
            {
                value = (java.math.BigDecimal)(((java.math.BigDecimal)field_vinkel.getOldValue()));//$JR_EXPR_ID=45$
                break;
            }
            case 46 : 
            {
                value = (java.lang.String)(String.valueOf("Side ") + String.valueOf(((java.lang.Integer)variable_PAGE_NUMBER.getOldValue())) +  String.valueOf(" av "));//$JR_EXPR_ID=46$
                break;
            }
            case 47 : 
            {
                value = (java.lang.Integer)(((java.lang.Integer)variable_PAGE_NUMBER.getOldValue()));//$JR_EXPR_ID=47$
                break;
            }
            case 48 : 
            {
                value = (java.math.BigDecimal)(((java.math.BigDecimal)variable_SUM_beregnetTid_1.getOldValue()));//$JR_EXPR_ID=48$
                break;
            }
            case 49 : 
            {
                value = (java.lang.String)(String.valueOf("Side ") + String.valueOf(((java.lang.Integer)variable_PAGE_NUMBER.getOldValue())) +  String.valueOf(" av "));//$JR_EXPR_ID=49$
                break;
            }
            case 50 : 
            {
                value = (java.lang.Integer)(((java.lang.Integer)variable_PAGE_NUMBER.getOldValue()));//$JR_EXPR_ID=50$
                break;
            }
           default :
           {
           }
        }
        
        return value;
    }


    /**
     *
     */
    public Object evaluateEstimated(int id) throws Throwable
    {
        Object value = null;

        switch (id)
        {
            case 0 : 
            {
                value = (java.lang.Integer)(new Integer(1));//$JR_EXPR_ID=0$
                break;
            }
            case 1 : 
            {
                value = (java.lang.Integer)(new Integer(1));//$JR_EXPR_ID=1$
                break;
            }
            case 2 : 
            {
                value = (java.lang.Integer)(new Integer(1));//$JR_EXPR_ID=2$
                break;
            }
            case 3 : 
            {
                value = (java.lang.Integer)(new Integer(0));//$JR_EXPR_ID=3$
                break;
            }
            case 4 : 
            {
                value = (java.lang.Integer)(new Integer(1));//$JR_EXPR_ID=4$
                break;
            }
            case 5 : 
            {
                value = (java.lang.Integer)(new Integer(0));//$JR_EXPR_ID=5$
                break;
            }
            case 6 : 
            {
                value = (java.lang.Integer)(new Integer(1));//$JR_EXPR_ID=6$
                break;
            }
            case 7 : 
            {
                value = (java.lang.Integer)(new Integer(0));//$JR_EXPR_ID=7$
                break;
            }
            case 8 : 
            {
                value = (java.math.BigDecimal)(((java.math.BigDecimal)field_beregnetTid.getValue()));//$JR_EXPR_ID=8$
                break;
            }
            case 9 : 
            {
                value = (java.lang.String)("Takstolinfo - Ordre " + ((java.lang.String)field_ordernr.getValue()));//$JR_EXPR_ID=9$
                break;
            }
            case 10 : 
            {
                value = (java.io.InputStream)(((java.io.InputStream)parameter_ugland_logo.getValue()));//$JR_EXPR_ID=10$
                break;
            }
            case 11 : 
            {
                value = (java.lang.String)("�nsket uke " + ((java.lang.Integer)field_oensketUke.getValue()));//$JR_EXPR_ID=11$
                break;
            }
            case 12 : 
            {
                value = (java.lang.String)(((java.lang.String)field_ordernr.getValue()));//$JR_EXPR_ID=12$
                break;
            }
            case 13 : 
            {
                value = (java.lang.Integer)(((java.lang.Integer)field_kundenr.getValue()));//$JR_EXPR_ID=13$
                break;
            }
            case 14 : 
            {
                value = (java.lang.String)(((java.lang.String)field_navn.getValue()));//$JR_EXPR_ID=14$
                break;
            }
            case 15 : 
            {
                value = (java.lang.String)(((java.lang.String)field_leveringsadresse.getValue()));//$JR_EXPR_ID=15$
                break;
            }
            case 16 : 
            {
                value = (java.lang.String)(((java.lang.String)field_postnr.getValue())+" "+((java.lang.String)field_poststed.getValue()));//$JR_EXPR_ID=16$
                break;
            }
            case 17 : 
            {
                value = (java.lang.Boolean)(Boolean.valueOf(((java.lang.Integer)field_hoydeOverHavet.getValue()).intValue()>0));//$JR_EXPR_ID=17$
                break;
            }
            case 18 : 
            {
                value = (java.lang.Integer)(((java.lang.Integer)field_hoydeOverHavet.getValue()));//$JR_EXPR_ID=18$
                break;
            }
            case 19 : 
            {
                value = (java.lang.String)(((java.lang.String)field_beregnetFor.getValue()));//$JR_EXPR_ID=19$
                break;
            }
            case 20 : 
            {
                value = (java.lang.Boolean)(Boolean.valueOf(((java.lang.Integer)field_snolast.getValue()).intValue()>0));//$JR_EXPR_ID=20$
                break;
            }
            case 21 : 
            {
                value = (java.lang.Integer)(((java.lang.Integer)field_snolast.getValue()));//$JR_EXPR_ID=21$
                break;
            }
            case 22 : 
            {
                value = (java.lang.Boolean)(Boolean.valueOf(((java.lang.Integer)field_egenvekt.getValue()).intValue()>0));//$JR_EXPR_ID=22$
                break;
            }
            case 23 : 
            {
                value = (java.lang.Integer)(((java.lang.Integer)field_egenvekt.getValue()));//$JR_EXPR_ID=23$
                break;
            }
            case 24 : 
            {
                value = (java.lang.Integer)(((java.lang.Integer)field_utstikkType.getValue()));//$JR_EXPR_ID=24$
                break;
            }
            case 25 : 
            {
                value = (java.io.InputStream)(((java.io.InputStream)parameter_utstikk_bilde.getValue()));//$JR_EXPR_ID=25$
                break;
            }
            case 26 : 
            {
                value = (java.lang.String)(((java.lang.String)field_tlfKunde.getValue()));//$JR_EXPR_ID=26$
                break;
            }
            case 27 : 
            {
                value = (java.lang.String)(((java.lang.String)field_tlfByggeplass.getValue()));//$JR_EXPR_ID=27$
                break;
            }
            case 28 : 
            {
                value = (java.lang.String)(((java.lang.String)field_kundeRef.getValue()));//$JR_EXPR_ID=28$
                break;
            }
            case 29 : 
            {
                value = (java.lang.String)(((java.lang.String)field_trossDrawer.getValue()));//$JR_EXPR_ID=29$
                break;
            }
            case 30 : 
            {
                value = (java.lang.Integer)(((java.lang.Integer)field_maksHoyde.getValue()));//$JR_EXPR_ID=30$
                break;
            }
            case 31 : 
            {
                value = (java.lang.String)(((java.lang.String)field_kode.getValue()));//$JR_EXPR_ID=31$
                break;
            }
            case 32 : 
            {
                value = (java.math.BigDecimal)(((java.math.BigDecimal)field_antall.getValue()));//$JR_EXPR_ID=32$
                break;
            }
            case 33 : 
            {
                value = (java.lang.String)(((java.lang.String)field_prodno.getValue()));//$JR_EXPR_ID=33$
                break;
            }
            case 34 : 
            {
                value = (java.lang.String)(((java.lang.String)field_beskrivelse.getValue()));//$JR_EXPR_ID=34$
                break;
            }
            case 35 : 
            {
                value = (java.lang.String)(((java.lang.String)field_takstoltype.getValue()));//$JR_EXPR_ID=35$
                break;
            }
            case 36 : 
            {
                value = (java.lang.Boolean)(Boolean.valueOf(((java.math.BigDecimal)field_virkesbredde.getValue()).intValue()>0));//$JR_EXPR_ID=36$
                break;
            }
            case 37 : 
            {
                value = (java.math.BigDecimal)(((java.math.BigDecimal)field_virkesbredde.getValue()));//$JR_EXPR_ID=37$
                break;
            }
            case 38 : 
            {
                value = (java.lang.Boolean)(Boolean.valueOf(((java.lang.Integer)field_utstikkslengde.getValue()).intValue()>0));//$JR_EXPR_ID=38$
                break;
            }
            case 39 : 
            {
                value = (java.lang.Integer)(((java.lang.Integer)field_utstikkslengde.getValue()));//$JR_EXPR_ID=39$
                break;
            }
            case 40 : 
            {
                value = (java.lang.Boolean)(Boolean.valueOf(((java.math.BigDecimal)field_nedstikk.getValue()).intValue()>0));//$JR_EXPR_ID=40$
                break;
            }
            case 41 : 
            {
                value = (java.math.BigDecimal)(((java.math.BigDecimal)field_nedstikk.getValue()));//$JR_EXPR_ID=41$
                break;
            }
            case 42 : 
            {
                value = (java.math.BigDecimal)(((java.math.BigDecimal)field_beregnetTid.getValue()));//$JR_EXPR_ID=42$
                break;
            }
            case 43 : 
            {
                value = (java.lang.Boolean)(Boolean.valueOf(((java.math.BigDecimal)field_spennvidde.getValue()).intValue()>0));//$JR_EXPR_ID=43$
                break;
            }
            case 44 : 
            {
                value = (java.math.BigDecimal)(((java.math.BigDecimal)field_spennvidde.getValue()));//$JR_EXPR_ID=44$
                break;
            }
            case 45 : 
            {
                value = (java.math.BigDecimal)(((java.math.BigDecimal)field_vinkel.getValue()));//$JR_EXPR_ID=45$
                break;
            }
            case 46 : 
            {
                value = (java.lang.String)(String.valueOf("Side ") + String.valueOf(((java.lang.Integer)variable_PAGE_NUMBER.getEstimatedValue())) +  String.valueOf(" av "));//$JR_EXPR_ID=46$
                break;
            }
            case 47 : 
            {
                value = (java.lang.Integer)(((java.lang.Integer)variable_PAGE_NUMBER.getEstimatedValue()));//$JR_EXPR_ID=47$
                break;
            }
            case 48 : 
            {
                value = (java.math.BigDecimal)(((java.math.BigDecimal)variable_SUM_beregnetTid_1.getEstimatedValue()));//$JR_EXPR_ID=48$
                break;
            }
            case 49 : 
            {
                value = (java.lang.String)(String.valueOf("Side ") + String.valueOf(((java.lang.Integer)variable_PAGE_NUMBER.getEstimatedValue())) +  String.valueOf(" av "));//$JR_EXPR_ID=49$
                break;
            }
            case 50 : 
            {
                value = (java.lang.Integer)(((java.lang.Integer)variable_PAGE_NUMBER.getEstimatedValue()));//$JR_EXPR_ID=50$
                break;
            }
           default :
           {
           }
        }
        
        return value;
    }


}