package sg.edu.nus.studentmanagementsystem.models.viewmodels;

public class ModuleQuery {
	private String modulecode;
	private String modulename;
	public String getModulecode() {
		return modulecode;
	}
	
	
	public ModuleQuery() {

	}


	public void setModulecode(String modulecode) {
		this.modulecode = modulecode;
	}
	public String getModulename() {
		return modulename;
	}
	public void setModulename(String modulename) {
		this.modulename = modulename;
	}

	public ModuleQuery(String modulecode, String modulename, String modulecredit) {
		super();
		this.modulecode = modulecode;
		this.modulename = modulename;
	}
	
	
	
}
