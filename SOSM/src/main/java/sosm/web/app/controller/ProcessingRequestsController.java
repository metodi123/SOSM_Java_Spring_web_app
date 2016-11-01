package sosm.web.app.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sosm.web.app.dao.ModuleDAO;
import sosm.web.app.dao.SelectionsStateDAO;
import sosm.web.app.dao.StudentDAO;
import sosm.web.app.dao.StudentRequestDAO;
import sosm.web.app.model.Employee;
import sosm.web.app.model.Module;
import sosm.web.app.model.RequestSelectionType;
import sosm.web.app.model.SelectionsState;
import sosm.web.app.model.Student;
import sosm.web.app.model.StudentRequest;
import sosm.web.app.model.User;
import sosm.web.app.service.DatabaseConnectionService;
import sosm.web.app.service.ModuleParametersValidationService;
import sosm.web.app.service.StudentRequestProcessingService;

@Controller
@RequestMapping(value = "/employee")
public class ProcessingRequestsController {
	
	@RequestMapping(value = "/findRequests", method = RequestMethod.POST)
	public String findRequests(HttpServletRequest request,
			@RequestParam(required = false, value="requestsState") String requestsState,
			@RequestParam(required = false, value="requestsSemester") List<String> requestsSemester,
			@RequestParam(required = false, value="requestsType") List<String> requestsType,
			RedirectAttributes redirectAttributes) {
		if(request.getSession(false) == null) {
			return "redirect:/employee";
		}
		
		if(!(request.getSession(false).getAttribute(User.currentUser) instanceof Employee)) {
			return "redirect:/error403";
		}
		
		redirectAttributes.addFlashAttribute("requestsState", requestsState);
		redirectAttributes.addFlashAttribute("requestsSemester", requestsSemester);
		redirectAttributes.addFlashAttribute("requestsType", requestsType);

		return "redirect:/employee/showRequests";
	}
	
	@RequestMapping(value = "/getRequestsToProcess", method = RequestMethod.POST)
	public String getRequestsToProcess(HttpServletRequest request,
			@RequestParam("selectionType") String selectionType,
			@RequestParam("semester") int semester,
			RedirectAttributes redirectAttributes) {
		if(request.getSession(false) == null) {
			return "redirect:/employee";
		}
		
		if(!(request.getSession(false).getAttribute(User.currentUser) instanceof Employee)) {
			return "redirect:/error403";
		}
		
		int moduleId = 0;
		
		if(selectionType.contains("-")) {
			StringTokenizer tokenizer = new StringTokenizer(selectionType, "-");
			selectionType = tokenizer.nextToken();
			moduleId = Integer.parseInt(tokenizer.nextToken());
		}
		
		StudentRequestProcessingService studentRequestProcessingService = new StudentRequestProcessingService();
		
		if(RequestSelectionType.valueOf(selectionType) == RequestSelectionType.electiveCourse) {
			try {
				if(studentRequestProcessingService.semesterContainsUnprocessedRequests(semester, RequestSelectionType.module)) {
					redirectAttributes.addFlashAttribute("message", "ModuleSelectionHasNotBeenProcessedYet");
					return "redirect:/employee/chooseRequestsToProcess";
				}
			} catch (Exception e) {
				e.printStackTrace();
				redirectAttributes.addAttribute("message", "DatabaseError");
				return "redirect:/error";
			} 
		}
		if((RequestSelectionType.valueOf(selectionType) == RequestSelectionType.courseProject)) {
			try {
				if(studentRequestProcessingService.semesterContainsUnprocessedRequests(semester, RequestSelectionType.module)) {
					redirectAttributes.addFlashAttribute("message", "ModuleSelectionHasNotBeenProcessedYet");
					return "redirect:/employee/chooseRequestsToProcess";
				}
				if(studentRequestProcessingService.semesterContainsUnprocessedRequests(semester, RequestSelectionType.electiveCourse)) {
					redirectAttributes.addFlashAttribute("message", "ElectiveCourseSelectionHasNotBeenProcessedYet");
					return "redirect:/employee/chooseRequestsToProcess";
				}
			} catch (Exception e) {
				e.printStackTrace();
				redirectAttributes.addAttribute("message", "DatabaseError");
				return "redirect:/error";
			} 
		}
		if((RequestSelectionType.valueOf(selectionType) == RequestSelectionType.courseWork)) {
			try {
				if(studentRequestProcessingService.semesterContainsUnprocessedRequests(semester, RequestSelectionType.module)) {
					redirectAttributes.addFlashAttribute("message", "ModuleSelectionHasNotBeenProcessedYet");
					return "redirect:/employee/chooseRequestsToProcess";
				}
				if(studentRequestProcessingService.semesterContainsUnprocessedRequests(semester, RequestSelectionType.electiveCourse)) {
					redirectAttributes.addFlashAttribute("message", "ElectiveCourseSelectionHasNotBeenProcessedYet");
					return "redirect:/employee/chooseRequestsToProcess";
				}
				if(studentRequestProcessingService.semesterContainsUnprocessedRequests(semester, RequestSelectionType.courseProject)) {
					redirectAttributes.addFlashAttribute("message", "CourseProjectSelectionHasNotBeenProcessedYet");
					return "redirect:/employee/chooseRequestsToProcess";
				}
			} catch (Exception e) {
				e.printStackTrace();
				redirectAttributes.addAttribute("message", "DatabaseError");
				return "redirect:/error";
			} 
		}
		
		SelectionsState selectionsState = new SelectionsState();
		
		SelectionsStateDAO selectionsStateDAO = new SelectionsStateDAO();
			
		try {
			selectionsState = selectionsStateDAO.getSelectionsState();
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		if((semester == 4) && (selectionsState.isSelectionOpenForSemester4() == true) ||
			(semester == 5) && (selectionsState.isSelectionOpenForSemester5() == true) ||
			((semester == 6) && ((selectionsState.isSelectionOpenForSemester6() == true) || (selectionsState.isSelectionOpenModules() == true))) ||
			(semester == 7) && (selectionsState.isSelectionOpenForSemester7() == true)) {
			
			redirectAttributes.addFlashAttribute("message", "SelectionIsOpen");
			return "redirect:/employee/chooseRequestsToProcess";
		}
		
		try {
			studentRequestProcessingService.loadNotProvidedRequests(semester, moduleId, RequestSelectionType.valueOf(selectionType));
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		redirectAttributes.addFlashAttribute("selectionType", selectionType);
		redirectAttributes.addFlashAttribute("semester", semester);
		redirectAttributes.addFlashAttribute("moduleId", moduleId);

		return "redirect:/employee/allocateRequests";
	}
	
	@RequestMapping(value = "/processingStudentsRequests", method = RequestMethod.POST)
	public String processingStudentsRequests(Model model, HttpServletRequest request,
		@RequestParam Map<String,String> selectionUnits,
		@RequestParam("semester") int semester,
		@RequestParam("selectionType") String selectionType,
		@RequestParam("numberOfRequests") int numberOfRequests,
		@RequestParam("moduleId") int moduleId,
		RedirectAttributes redirectAttributes) {
		
		if(request.getSession(false) == null) {
			return "redirect:/employee";
		}
		
		if(!(request.getSession(false).getAttribute(User.currentUser) instanceof Employee)) {
			return "redirect:/error403";
		}
		
		Connection connection = null;
		try {
			connection = DatabaseConnectionService.getAppDatabaseConnection();
		} catch (Exception e1) {
			e1.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
		
		selectionUnits.remove("semester");
		selectionUnits.remove("selectionType");
		selectionUnits.remove("numberOfRequests");
		selectionUnits.remove("moduleId");
		
		int totalPlaces = 0;
		
		for (Map.Entry<String, String> entry : selectionUnits.entrySet()) {
			totalPlaces += Integer.parseInt(entry.getValue());
		}
		
		if(totalPlaces != numberOfRequests) {
        	redirectAttributes.addAttribute("message", "EnteredInvalidNumberOfPlaces");
    		return "redirect:/error";
        }

        StudentRequestDAO studentRequestDAO = new StudentRequestDAO();
        
        StudentRequestProcessingService studentRequestProcessingService = new StudentRequestProcessingService();
        
        List<StudentRequest> allStudentRequests = new ArrayList<StudentRequest>();
        List<StudentRequest> studentRequests = new ArrayList<StudentRequest>();
        
        try {
        	allStudentRequests = studentRequestDAO.getAllStudentRequests();
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}
        
        Module module = new Module();
        
        ModuleDAO moduleDAO = new ModuleDAO();
        
        ModuleParametersValidationService moduleParametersValidationService = new ModuleParametersValidationService();
        
        if(moduleId != 0) {
        	try {
				module = moduleDAO.getModule(moduleId);
			} catch (Exception e) {
				e.printStackTrace();
				redirectAttributes.addAttribute("message", "DatabaseError");
				return "redirect:/error";
			} 
        }
        
        for(StudentRequest studentRequest : allStudentRequests) {
        	try {
				if((studentRequest.getSemester() == semester) && (module.getName() == null || moduleParametersValidationService.isModuleValidForStudent(module, studentRequest.getFacutyNumber())) && (studentRequest.getSelectionType() == RequestSelectionType.valueOf(selectionType)) && studentRequest.isAccepted() == false) {
					studentRequests.add(studentRequest);
				}
			} catch (Exception e) {
				e.printStackTrace();
				redirectAttributes.addAttribute("message", "DatabaseError");
				return "redirect:/error";
			}
        }
        
        try {
			studentRequestProcessingService.processRequests(studentRequests, selectionUnits);
		} catch (Exception e1) {
			e1.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}

        if(RequestSelectionType.valueOf(selectionType) == RequestSelectionType.electiveCourse) {
        	try {
				studentRequestProcessingService.updateRequestsToMeetSelectedCriteria(semester, RequestSelectionType.electiveCourse, RequestSelectionType.courseProject);
			} catch (Exception e) {
				e.printStackTrace();
				redirectAttributes.addAttribute("message", "DatabaseError");
				return "redirect:/error";
			}
        }
        else if(RequestSelectionType.valueOf(selectionType) == RequestSelectionType.courseProject) {
        	try {
				studentRequestProcessingService.updateRequestsToMeetSelectedCriteria(semester, RequestSelectionType.courseProject, RequestSelectionType.courseWork);
			} catch (Exception e) {
				e.printStackTrace();
				redirectAttributes.addAttribute("message", "DatabaseError");
				return "redirect:/error";
			}
        }
        
        model.addAttribute("studentsRequestsNewList", studentRequests);
        
		return "newly-processed-requests-employee";
	}
	
	@RequestMapping(value = "/changeSelectionsState", method = RequestMethod.POST)
	public String changeSelectionsState(HttpServletRequest request,
			@RequestParam("semester4") boolean semester4,
			@RequestParam("semester5") boolean semester5,
			@RequestParam("semester6") boolean semester6,
			@RequestParam("semester7") boolean semester7,
			@RequestParam("modules") boolean modules,
			RedirectAttributes redirectAttributes) {
		if(request.getSession(false) == null) {
			return "redirect:/employee";
		}
		
		if(!(request.getSession(false).getAttribute(User.currentUser) instanceof Employee)) {
			return "redirect:/error403";
		}
		
		SelectionsState selectionsState = new SelectionsState();
		
		selectionsState.setSelectionOpenForSemester4(semester4);
		selectionsState.setSelectionOpenForSemester5(semester5);
		selectionsState.setSelectionOpenForSemester6(semester6);
		selectionsState.setSelectionOpenForSemester7(semester7);
		selectionsState.setSelectionOpenModules(modules);
		
		SelectionsStateDAO selectionsStateDAO = new SelectionsStateDAO();
		
		try {
			selectionsStateDAO.setSelectionsState(selectionsState);
		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addAttribute("message", "DatabaseError");
			return "redirect:/error";
		}

		return "redirect:/employee";
	}
	
}
