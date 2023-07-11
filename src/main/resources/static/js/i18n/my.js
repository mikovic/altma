$( document ).ready(function() {

loadBundles();
function loadBundles() {
			jQuery.i18n.properties({
			    name:'message',
			    path:'js/i18n/',
			    mode:'both',
               async: true

			});
		}







   var test = [
         ["1", "10", "n1","n2","n3","n4"],
         ["2", "10", "n1","n2","n3","n4"],
         ["3", "10", "n1","n2","n3","n4"],
         ["2", "20", "n1m","n2m","n3m"],
         ["3", "30", "property.penthouse"],
         ["4", "40", "type.cottage","type.snt","type.village","type.town"],
         ["5", "50", "n1m","n2m","n3m"],
         ["6", "60", "type.garage.cooperative","type.garage.house"],
         ["7", "70", "type.comercial.shop","type.comercial.office",
          "type.comercial.hotel","type.comercial.shop.center","type.comercial.office.center",
          "type.comercial.manuf.build","type.comercial.build"]
        ];

$("#s1").on("change", function(){
    var value = $(this).val();
    if (1 <= value && value <= 4 ){

     $("#my-multi").empty();
 $("#my-multi").prop("title", "");

     $("#my-multi").prop("title", "КККККККК");

     } else {
     $("#my-multi").empty();
 $("#my-multi").prop("title", "");
       $("#my-multi").prop("title", "Цена");
     }


    $("#my-multi").selectpicker('refresh');

});




//      $("#s1").on("change", function(){
//          var value = $(this).val();
//          $("#my-multi").empty();
//          for(var i=0;i<test.length;i++){
//              if(test[i][0]==value){
//                  var val = test[i][1]
//                  var val2 = jQuery.i18n.prop(test[i][2]);
//
//                   for(var j=2,l=test[i].length; j<l; j++){
//                      var val2 = jQuery.i18n.prop(test[i][j]);
//                      $("#my-multi").append("<option th:value = " +val + ">"+val2+"</option>");
//                      val++;
//                  }
//
//              }
//          }
//
//
//          $("#my-multi").selectpicker('refresh');
//
//      });
//
//




//$('#price').on("keyup", function(){
//
//  var msg;
//  var ValuePrice = $('#price').val();
//
//  if(ValuePrice ==""){
//        return;
//  }
//
//
//
//  if(100 <= ValuePrice  && ValuePrice < 1000000) {
//
//       $('#msg').val('');
//       msg = 'от ' + (ValuePrice/1000).toFixed(1) + " тыс";
//       $('#msg').val(msg);
//       num = $('#price').val();
//       return;
//      }
//
//   if(1000000 <= ValuePrice && ValuePrice < 1000000000) {
//
//         $('#msg').val('');
//         msg = 'от '+ (ValuePrice/1000000).toFixed(1) + " млн";
//         $('#msg').val(msg);
//         num = $('#price').val();
//         return;
//        }
//    if( 1000000000 <= ValuePrice && ValuePrice < 100000000000) {
//
//         $('#msg').val('');
//          msg = 'от '+ (ValuePrice/1000000000).toFixed(1) + " млрд";
//         $('#msg').val(msg);
//         num = $('#price').val();
//         return;
//        }
//     if( 100000000000 <= ValuePrice) {
//
//         $('#msg').val('');
//          msg = " от 100 млрд";
//         $('#msg').val(msg);
//         num = $('#price').val();
//         return;
//        }
//
//
//
//});

//$('#price2').on("keyup", function(){
//
//
//   var ValuePrice2 = $("#price2").val();
//
//   if(ValuePrice2 == ""){
//           return;
//     }
//   var ValuePrice = $("#price").val();
//
//
//     if(parseInt(ValuePrice) >= parseInt(ValuePrice2)){
//        $("#price2").addClass("price");
//        $("#price").addClass("price");
//
//       }else{
//         $("#price2").removeClass("price");
//         $("#price").removeClass("price");
//       }
//       var msg1 = "";
//       var msg2 = "";
//      if(100 <= ValuePrice  && ValuePrice < 1000000) {
//
//
//            msg1 = (ValuePrice/1000).toFixed(1) + " тыс";
//
//           }
//
//        if(1000000 <= ValuePrice && ValuePrice < 1000000000) {
//
//
//              msg1 = (ValuePrice/1000000).toFixed(1) + " млн";
//
//             }
//         if( 1000000000 <= ValuePrice && ValuePrice < 100000000000) {
//
//
//               msg1 = (ValuePrice/1000000000).toFixed(1) + " млрд";
//
//             }
//          if( 100000000000 <= ValuePrice) {
//
//
//               msg1 = "100 млрд";
//
//             }
//       if(100 <= ValuePrice2  && ValuePrice2 < 1000000) {
//
//              $('#msg').val('');
//              msg2 = ' - ' + (ValuePrice2/1000).toFixed(1) + " тыс";
//              $('#msg').val(msg1 + msg2);
//
//              return;
//             }
//
//          if(1000000 <= ValuePrice2 && ValuePrice2 < 1000000000) {
//
//                $('#msg').val('');
//                msg2 = ' - '+ (ValuePrice2/1000000).toFixed(1) + " млн";
//                $('#msg').val(msg1 + msg2);
//                return;
//               }
//           if( 1000000000 <= ValuePrice2 && ValuePrice2 < 100000000000) {
//
//                $('#msg').val('');
//                 msg2 = ' - '+ (ValuePrice2/1000000000).toFixed(1) + " млрд";
//                $('#msg').val(msg1 + msg2);
//                return;
//               }
//            if( 100000000000 <= ValuePrice2) {
//
//                $('#msg').val('');
//                 msg2 = "  ...";
//                $('#msg').val(msg1 + msg2);
//
//                return;
//               }
//
//
//});
//






});



