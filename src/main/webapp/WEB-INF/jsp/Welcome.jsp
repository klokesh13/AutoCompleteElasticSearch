<!DOCTYPE html>
<html>
 <head>
  <!-- <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" />
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script> -->
  
  <link rel="stylesheet" href="/css/bootstrap.min.css" /> 
  <script src="/js/bootstrap.min.js"></script>
  <script src="/js/jquery-1.12.4.js"></script>
  <script src="/js/jquery-ui.js"></script>
  
  <style>
  #result {
   position: absolute;
   width: 100%;
   max-width:1070px;
   cursor: pointer;
   overflow-y: auto;
   max-height: 650px;
   box-sizing: border-box;
   z-index: 1001;
  }
  .link-class:hover{
   background-color:#f1f1f1;
  }
  
li span { 
	display: inline-block;
	/* width: 225px; */
	width: 300px;
}

body {
    font-family: "Helvetica Neue",Helvetica,Arial,sans-serif;
    font-size: 14px;
    line-height: 1.42857143;
    color: #354;
    background-color: #FFC;
}
  </style>
  
  <script>
  $( function() {
    
    $( "#user" ).autocomplete({
        source: function( request, response ) {
          $.ajax({
            url: "/matches",
            dataType: "json",
            data: {
              q: request.term
            },
            success: function( data ) {
            	/* alert(data); */
            	var json = data;
            	$('#result').html('');
            	$.each(json, function(key, value){
            		
            		if(value.vehicleNumber == null) {
            			$('#result').append('<li class="list-group-item link-class"><span class="text-muted"><img src="/images/user-alt.png" height="40" width="40" class="img-thumbnail" /> '+value.name+
                				'</span> | <span class="text-muted"> <img src="/images/mail-alt.jpeg" height="40" width="40" class="img-thumbnail" /> '+value.email+
                				/* '</span> <span class="text-muted"> <img src="/images/account-alt.png" height="40" width="40" class="img-thumbnail" /> '+value.accountId+
                				'</span> <span class="text-muted"> <img src="/images/system-alt.png" height="40" width="40" class="img-thumbnail" /> '+value.systemId+ */
                				'</span></li>');
            		} else {
            			$('#result').append('<li class="list-group-item link-class"><span class="text-muted"><img src="/images/vehicle-alt.png" height="40" width="40" class="img-thumbnail" /> '+value.vehicleNumber+
                				/* '</span> <span class="text-muted"> <img src="/images/account-alt.jpeg" height="40" width="40" class="img-thumbnail" /> '+value.accountId+
                				'</span> <span class="text-muted"> <img src="/images/system-alt.png" height="40" width="40" class="img-thumbnail" /> '+value.systemId+ */
                				'</span></li>');
            		}
            		
            	});   
            }
          });
        },
        minLength: 0
      });
    
    $('#result').on('click', 'li', function() {
    	  var click_text = $(this).text().split('|');
    	  $('#user').val($.trim(click_text[0]));
    	  $("#result").html('');
    	 });

    
  });
  </script>
  
 </head>
 <body>
  <br /><br />
  <div class="container" style="width:1100px;">
   <h2 align="center">AutoComplete Search on Users and Vehicles data</h2>
   <!-- <h3 align="center">Users Data</h3>  -->  
   <br /><br />
   <div align="center">
    <input type="text" id="user" placeholder="Type here to Search.." class="form-control" />
   </div>
   <ul class="list-group" id="result"></ul>
   <br />
  </div>
 </body>
</html>


