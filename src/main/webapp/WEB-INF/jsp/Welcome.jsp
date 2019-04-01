<!DOCTYPE html>
<html>
 <head>
  <!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script> -->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" />
  
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  
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
            			$('#result').append('<li class="list-group-item link-class"><span class="text-muted"><img src="http://iconshow.me/media/images/Mixed/small-n-flat-icon/png/512/user-alt.png" height="40" width="40" class="img-thumbnail" /> '+value.name+
                				'</span> | <span class="text-muted"> <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRW9Bg8mZx27rEg_Sy1SlIrRJL09hNsyfjuzugN3uyT-hQm9lCp" height="40" width="40" class="img-thumbnail" /> '+value.email+
                				/* '</span> <span class="text-muted"> <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ69ezfAM0xaZ1LAIN2BvkoffyploK6S65EjSg-RSdkjZhh6x5i" height="40" width="40" class="img-thumbnail" /> '+value.accountId+
                				'</span> <span class="text-muted"> <img src="data:image/png;base64,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" height="40" width="40" class="img-thumbnail" /> '+value.systemId+ */
                				'</span></li>');
            		} else {
            			$('#result').append('<li class="list-group-item link-class"><span class="text-muted"><img src="https://cdn.iconscout.com/icon/free/png-256/truck-shipping-logistic-delivery-transport-supply-vehicle-20264.png" height="40" width="40" class="img-thumbnail" /> '+value.vehicleNumber+
                				/* '</span> <span class="text-muted"> <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ69ezfAM0xaZ1LAIN2BvkoffyploK6S65EjSg-RSdkjZhh6x5i" height="40" width="40" class="img-thumbnail" /> '+value.accountId+
                				'</span> <span class="text-muted"> <img src="data:image/png;base64,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" height="40" width="40" class="img-thumbnail" /> '+value.systemId+ */
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


