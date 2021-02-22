package cycbersoft.java10.entity;

public class Project {
	private int id;
	private String name;
	private String description;
	private String startDate;
	private String endDate;
	private int createUser;
	private String fullName;
	
	public Project() {
		
	}
	
	public Project(String name, String description, String startDate, String endDate, int createUser, int id) {
		super();
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.createUser = createUser;
		this.id = id;
	}

	public Project(String name, String description, String startDate, String endDate, int createUser) {
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.createUser = createUser;
	}
	
	
	
	public Project(String name, String startDate, String endDate, int id) {
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCreateUser() {
		return createUser;
	}

	public void setCreateUser(int createUser) {
		this.createUser = createUser;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
}