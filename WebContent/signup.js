$(document).on("click", "#signupSub", function validatedMyform() {
	
	if($("#firstP").val() != $("#secondP").val()){
		alert("Two times of password are different!");
		returnToPreviousPage();
        return false;
	}
	return true;
});