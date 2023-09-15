package no.ugland.utransprod.model;

import java.sql.Timestamp;
import java.util.Date;

public class ProductionTime extends BaseObject {
	private Integer productionTimeId;
	private String orderNr;
	private String productionname;
	private String username;
	private String name;
	private Date created;
	private Date updated;
	private Date started;
	private Date stopped;
	private Integer overtime;
	private Integer productionWeek;

	public ProductionTime() {
		// TODO Auto-generated constructor stub
	}

	public ProductionTime(Integer productionTimeId, String orderNr, String productionname, String username, String name,
			Date created, Date updated, Date started, Date stopped, Integer overtime, Integer productionWeek) {
		super();
		this.productionTimeId = productionTimeId;
		this.orderNr = orderNr;
		this.productionname = productionname;
		this.username = username;
		this.name = name;
		this.created = created;
		this.updated = updated;
		this.started = started;
		this.stopped = stopped;
		this.overtime = overtime;
		this.productionWeek = productionWeek;
	}

	public ProductionTime(Integer productionTimeId, String orderNr, String productionname, String username, String name,
			Timestamp created, Timestamp updated, Timestamp started, Timestamp stopped, Integer overtime) {
		super();
		this.productionTimeId = productionTimeId;
		this.orderNr = orderNr;
		this.productionname = productionname;
		this.username = username;
		this.name = name;
		this.created = created;
		this.updated = updated;
		this.started = started;
		this.stopped = stopped;
		this.overtime = overtime;
	}

	public Integer getProductionWeek() {
		return productionWeek;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getProductionTimeId() {
		return productionTimeId;
	}

	public void setProductionTimeId(Integer productionTimeId) {
		this.productionTimeId = productionTimeId;
	}

	public String getOrderNr() {
		return orderNr;
	}

	public void setOrderNr(String orderNr) {
		this.orderNr = orderNr;
	}

	public String getProductionname() {
		return productionname;
	}

	public void setProductionname(String productionname) {
		this.productionname = productionname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public Date getStarted() {
		return started;
	}

	public void setStarted(Date started) {
		this.started = started;
	}

	public Date getStopped() {
		return stopped;
	}

	public void setStopped(Date stopped) {
		this.stopped = stopped;
	}

	public Integer getOvertime() {
		return overtime;
	}

	public void setOvertime(Integer overtime) {
		this.overtime = overtime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((orderNr == null) ? 0 : orderNr.hashCode());
		result = prime * result + ((productionTimeId == null) ? 0 : productionTimeId.hashCode());
		result = prime * result + ((productionname == null) ? 0 : productionname.hashCode());
		result = prime * result + ((started == null) ? 0 : started.hashCode());
		result = prime * result + ((stopped == null) ? 0 : stopped.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductionTime other = (ProductionTime) obj;
		if (orderNr == null) {
			if (other.orderNr != null)
				return false;
		} else if (!orderNr.equals(other.orderNr))
			return false;
		if (productionTimeId == null) {
			if (other.productionTimeId != null)
				return false;
		} else if (!productionTimeId.equals(other.productionTimeId))
			return false;
		if (productionname == null) {
			if (other.productionname != null)
				return false;
		} else if (!productionname.equals(other.productionname))
			return false;
		if (started == null) {
			if (other.started != null)
				return false;
		} else if (!started.equals(other.started))
			return false;
		if (stopped == null) {
			if (other.stopped != null)
				return false;
		} else if (!stopped.equals(other.stopped))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProductionTime [productionTimeId=" + productionTimeId + ", orderNr=" + orderNr + ", productionname="
				+ productionname + ", username=" + username + ", created=" + created + ", updated=" + updated
				+ ", started=" + started + ", stopped=" + stopped + "]";
	}

	public ProductionTime withOrderNr(String orderNr) {
		this.orderNr = orderNr;
		return this;
	}

	public ProductionTime withProductionname(String productionname) {
		this.productionname = productionname;
		return this;
	}

	public ProductionTime withUsername(String username) {
		this.username = username;
		return this;
	}

	public ProductionTime withStarted(Date startedDate) {
		this.started = startedDate;
		return this;
	}

	public ProductionTime withCreated(Date created) {
		this.created = created;
		return this;
	}

	public ProductionTime withUpdated(Date updated) {
		this.updated = updated;
		return this;
	}

	public ProductionTime withOvertime(boolean skalRegistreresSomOvertid) {
		this.overtime = skalRegistreresSomOvertid ? 1 : 0;
		return this;
	}

	public boolean getOvertimeBoolean() {
		return overtime == null || overtime == 0 ? false : true;
	}

}
