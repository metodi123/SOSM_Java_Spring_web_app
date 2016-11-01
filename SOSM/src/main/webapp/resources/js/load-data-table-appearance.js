$(document).ready(function() {
    $('#dataTable').DataTable( {
    	"sDom":
    	"<'row'<'col-sm-12'l>>" +
    	"<'row'<'col-sm-6'B><'col-sm-6'f>>" +
    	"<'row'<'col-sm-12'tr>>" +
    	"<'row'<'col-sm-5'i><'col-sm-7'p>>",
        buttons: [
            'copy', 'excel', 'pdf', 'print'
        ],
        "language": {
    	 	"sProcessing":   "Обработка на резултатите...",
    	    "sLengthMenu":   "Показване на _MENU_ резултата",
    	    "sZeroRecords":  "Няма намерени резултати",
    	    "sInfo":         "Показване на резултати от _START_ до _END_ от общо _TOTAL_",
    	    "sInfoEmpty":    "Показване на резултати от 0 до 0 от общо 0",
    	    "sInfoFiltered": "(филтрирани от общо _MAX_ резултата)",
    	    "sInfoPostFix":  "",
    	    "sSearch":       "Търсене във всички колони:",
    	    "sUrl":          "",
    	    "oPaginate": {
    	        "sFirst":    "Първа",
    	        "sPrevious": "Предишна",
    	        "sNext":     "Следваща",
    	        "sLast":     "Последна"
    	    }
    }
    } );
} );