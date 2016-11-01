package sosm.web.app.model;

import javax.activation.UnsupportedDataTypeException;

public enum RequestSelectionType {

	electiveCourse, courseWork, courseProject, module;
	
	@Override
	public String toString(){
        switch(this){
        case electiveCourse:
            return "избираема дисциплина";
        case courseWork:
            return "курсова работа";
        case courseProject:
            return "курсов проект";
        case module:
        	return "модул";
        }
        return null;
    }
	
	public static RequestSelectionType getEnum(String value) throws UnsupportedDataTypeException {
		if(value.equals("избираема дисциплина")) {
			return RequestSelectionType.electiveCourse;
		}
		else if(value.equals("курсова работа")) {
			return RequestSelectionType.courseWork;
		}
		else if(value.equals("курсов проект")) {
			return RequestSelectionType.courseProject;
		}
		else if(value.equals("модул")) {
			return RequestSelectionType.module;
		}
		else {
			throw new UnsupportedDataTypeException();
		}
    }
	
}
