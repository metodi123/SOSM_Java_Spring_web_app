package sosm.web.app.dao;

import java.util.List;

import sosm.web.app.model.Module;

public interface ModuleDataAccess {
	public Module getModule(int id) throws Exception;
	
	public List<Module> getAllModules() throws Exception;
	
	public void createModule(Module module) throws Exception;
	
	public void updateModuleInfo(Module module) throws Exception;
	
	public void deleteModule(int id) throws Exception;
}
