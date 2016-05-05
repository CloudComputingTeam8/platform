function onlyNum() 
{ 
if(!(event.keyCode==46)&&!(event.keyCode==8)&&!(event.keyCode==37)&&!(event.keyCode==39))
if(!((event.keyCode>=48&&event.keyCode<=57)||(event.keyCode>=96&&event.keyCode<=105))) 
event.returnValue=false; 
} 

var loadFile = function(event) {
 	$("#uploadimage").empty();
    var output = document.getElementById('uploadimage');
    output.src = URL.createObjectURL(event.target.files[0]);
  };
  
function openWindows(path){
	window.open("/CloudComputingTeam8/"+path);
	}

function message(appID){
	if(confirm("Are you sure to purchase?")){
		window.location.href="/CloudComputingTeam8/buyApp?appID="+appID;
		}
	else{
		alert("Purchase terminated");
		}
	}

function transaction(mycars,max) {
	var line = 3;
    $("#nextten").hide();
    changepagenum = 0;
    changedata = mycars;
    for(var i = changepagenum; i<changepagenum+line;i++){
    	if(mycars[i]!=null){
    	   	var dDsiplay = document.createTextNode(mycars[i]),
        	d1 = document.createElement('li');
        	dLI = document.getElementById('detaildisplay');
        	d1.appendChild(dDsiplay);
        	dLI.appendChild(d1);
    	}
 
}
    $("#lastten").click(function(){
    	changepagenum=changepagenum+line;
    	$('#nextten').show();
    	if(changepagenum>max-line){
    		$("#lastten").hide();
    		}
    	$("#detaildisplay").empty();    	
    	for(var i = changepagenum; i<changepagenum+line;i++){
    		if(changedata[i]!=null){
    			var dDsiplay = document.createTextNode(changedata[i]),d1 = document.createElement('li');
        		dLI = document.getElementById('detaildisplay');
        		d1.appendChild(dDsiplay);
        		dLI.appendChild(d1);
        		}   		
    		}
    	});
    
    $("#nextten").click(function () {
    	changepagenum=changepagenum-line;
    	$('#lastten').show();
    	if(changepagenum==0){
    		$("#nextten").hide();
    		}
    	$("#detaildisplay").empty();    	
    	for(var i = changepagenum; i<changepagenum+line;i++){
    		if(changedata[i]!=null){
    			var dDsiplay = document.createTextNode(changedata[i]),d1 = document.createElement('li');
        		dLI = document.getElementById('detaildisplay');
        		d1.appendChild(dDsiplay);
        		dLI.appendChild(d1);
        		} 
    		}
    	});
    }