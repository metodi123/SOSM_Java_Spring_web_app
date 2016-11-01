package sosm.web.app.model;

public enum UserType {

	Student, Employee, Admin;
	
	@Override
	public String toString(){
        switch(this){
        case Student :
            return "student";
        case Employee :
            return "employee";
        case Admin :
            return "admin";
        }
        return null;
    }
}
