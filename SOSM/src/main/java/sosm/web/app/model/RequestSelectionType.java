package sosm.web.app.model;

import javax.activation.UnsupportedDataTypeException;

public enum RequestSelectionType {

	electiveCourse, courseWork, courseProject, module;
	
	@Override
	public String toString(){
        switch(this){
        case electiveCourse:
            return "��������� ����������";
        case courseWork:
            return "������� ������";
        case courseProject:
            return "������ ������";
        case module:
        	return "�����";
        }
        return null;
    }
	
	public static RequestSelectionType getEnum(String value) throws UnsupportedDataTypeException {
		if(value.equals("��������� ����������")) {
			return RequestSelectionType.electiveCourse;
		}
		else if(value.equals("������� ������")) {
			return RequestSelectionType.courseWork;
		}
		else if(value.equals("������ ������")) {
			return RequestSelectionType.courseProject;
		}
		else if(value.equals("�����")) {
			return RequestSelectionType.module;
		}
		else {
			throw new UnsupportedDataTypeException();
		}
    }
	
}
