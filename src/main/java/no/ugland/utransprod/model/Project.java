package no.ugland.utransprod.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Project extends BaseObject {
    private Integer projectId;

    private String projectNumber;

    private String projectName;

    public Project() {
        super();
    }

    public Project(final Integer aProjectId, final String aProjectNumber,
            final String aProjectName) {
        super();
        this.projectId = aProjectId;
        this.projectNumber = aProjectNumber;
        this.projectName = aProjectName;
    }

    public final Integer getProjectId() {
        return projectId;
    }

    public final void setProjectId(final Integer aProjectId) {
        this.projectId = aProjectId;
    }

    public final String getProjectName() {
        return projectName;
    }

    public final void setProjectName(final String aProjectName) {
        this.projectName = aProjectName;
    }

    public final String getProjectNumber() {
        return projectNumber;
    }

    public final void setProjectNumber(final String aProjectNumber) {
        this.projectNumber = aProjectNumber;
    }

    @Override
    public final boolean equals(final Object other) {
        if (!(other instanceof Project)) {
            return false;
        }
        Project castOther = (Project) other;
        return new EqualsBuilder().append(projectId, castOther.projectId)
                .isEquals();
    }

    @Override
    public final int hashCode() {
        return new HashCodeBuilder().append(projectId).toHashCode();
    }

    @Override
    public final String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
                "projectId", projectId).append("projectNumber", projectNumber)
                .append("projectName", projectName).toString();
    }
}
