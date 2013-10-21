package no.ugland.utransprod.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import no.ugland.utransprod.model.Project;
import no.ugland.utransprod.test.FastTests;
import no.ugland.utransprod.util.ModelUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
@Category(FastTests.class)
public class ProjectManagerTest {
    private ProjectManager projectManager;
    
    @Before
    public void setUp() throws Exception {
        projectManager = (ProjectManager) ModelUtil.getBean("projectManager");
    }
    @Test
    public void testFindProjectByOrderNr(){
        Project project =projectManager.findByOrderNr("19");
        assertNotNull(project);
        assertEquals("1", project.getProjectNumber());
        assertEquals("test", project.getProjectName().trim());
    }
}
