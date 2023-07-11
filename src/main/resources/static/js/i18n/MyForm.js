import NRooms from './NRooms.js';


class Commerce extends React.Component {
componentDidMount() {
   this.list = $("#my-multi");
   this.list.selectpicker();

  }

  render() {
  const content = this.props.comercials.map((comercial) =>
           <option  value={comercial.value}>
               {comercial.type}
            </option>
         );

  return(
  <div>
     <select id="my-multi" defaultValue ={['20', '21']} class="my-multi form-control" multiple >
        {content}
     </select>
    </div>
    );

  }
}

class Houses extends React.Component {
componentDidMount() {
   this.list = $("#my-multi");
   this.list.selectpicker();

  }

  render() {
  const content = this.props.houses.map((house) =>
           <option  value={house.value}>
               {house.type}
            </option>
         );
  return(
   <div>
     <select id="my-multi"  defaultValue={['30']}  class="my-multi form-control" multiple >
        {content}
     </select>
   </div>
    );

  }
}

class Garages extends React.Component {
componentDidMount() {
   this.list = $("#my-multi");
   this.list.selectpicker();

  }

  render() {
  const content = this.props.garages.map((garage) =>
           <option  value={garage.value}>
               {garage.type}
            </option>
         );
  return(
   <div>
     <select id="my-multi"  defaultValue={['40']}  class="my-multi form-control" multiple >
        {content}
     </select>
   </div>
    );

  }
}


class Penthouses extends React.Component {
componentDidMount() {
   this.list = $("#my-multi");
   this.list.selectpicker();

  }

  render() {
  const content = this.props.penthouses.map((penthouse) =>
           <option  value={penthouse.value}>
               {penthouse.type}
            </option>
         );
  return(
   <div>
     <select id="my-multi"  defaultValue={['50']}  class="my-multi form-control" multiple >
        {content}
     </select>
   </div>
    );

  }
}

class Parts extends React.Component {
componentDidMount() {
   this.list = $("#my-multi");
   this.list.selectpicker();

  }

  render() {
  const content = this.props.parts.map((part) =>
           <option  value={part.value}>
               {part.type}
            </option>
         );
  return(
   <div>
     <select id="my-multi"  defaultValue={['60']}  class="my-multi form-control" multiple >
        {content}
     </select>
   </div>
    );

  }
}



class MyForm extends React.Component {

    constructor(props) {
    super(props);
    this.state = {value: 'flat'};
    this.handleChange = this.handleChange.bind(this);
    this.handleKeyUpMin = this.handleKeyUpMin.bind(this);
    this.handleKeyUpMax = this.handleKeyUpMax.bind(this);
  }

  handleChange(event) {

     const val = event.target.value;

      if(val == 'flat'|| val == 'flatnew' || val == 'resale' || val == 'apart') {
          this.setState({value: 'flat'});
       }


    if(val == 'penthouse') {
     this.setState({value: 'penthouse'});
    }
     if(val == 'another' ) {
          this.setState({value: 'another'});
        }
    if(val == 'house' ) {
      this.setState({value: 'house'});
    }

    if(val == 'garage') {
     this.setState({value: 'garage'});
     }
     if(val == 'commerce'){
      this.setState({value: 'commerce'});
     }
}
   handleKeyUpMin(event) {
         var msg;
          var ValuePrice = $('#price').val();

          if(ValuePrice ==""){
                return;
          }



          if(100 <= ValuePrice  && ValuePrice < 1000000) {

               $('#msg').html('');
               msg = 'от ' + (ValuePrice/1000).toFixed(1) + " тыс";
               $('#msg').html(msg);
               num = $('#price').val();
               return;
              }

           if(1000000 <= ValuePrice && ValuePrice < 1000000000) {

                 $('#msg').html('');
                 msg = 'от '+ (ValuePrice/1000000).toFixed(1) + " млн";
                 $('#msg').html(msg);
                 num = $('#price').val();
                 return;
                }
            if( 1000000000 <= ValuePrice && ValuePrice < 100000000000) {

                 $('#msg').html('');
                  msg = 'от '+ (ValuePrice/1000000000).toFixed(1) + " млрд";
                 $('#msg').html(msg);
                 num = $('#price').val();
                 return;
                }
             if( 100000000000 <= ValuePrice) {

                 $('#msg').html('');
                  msg = " от 100 млрд";
                 $('#msg').html(msg);
                 num = $('#price').val();
                 return;
                }



      }

       handleKeyUpMax(event){
          var ValuePrice2 = $("#price2").val();

             if(ValuePrice2 == ""){
                     return;
               }
             var ValuePrice = $("#price").val();


               if(parseInt(ValuePrice) >= parseInt(ValuePrice2)){
                  $("#price2").addClass("price");
                  $("#price").addClass("price");

                 }else{
                   $("#price2").removeClass("price");
                   $("#price").removeClass("price");
                 }
                 var msg1 = "";
                 var msg2 = "";
                if(100 <= ValuePrice  && ValuePrice < 1000000) {


                      msg1 = (ValuePrice/1000).toFixed(1) + " тыс";

                     }

                  if(1000000 <= ValuePrice && ValuePrice < 1000000000) {


                        msg1 = (ValuePrice/1000000).toFixed(1) + " млн";

                       }
                   if( 1000000000 <= ValuePrice && ValuePrice < 100000000000) {


                         msg1 = (ValuePrice/1000000000).toFixed(1) + " млрд";

                       }
                    if( 100000000000 <= ValuePrice) {


                         msg1 = "100 млрд";

                       }
                 if(100 <= ValuePrice2  && ValuePrice2 < 1000000) {

                        $('#msg').html('');
                        msg2 = ' - ' + (ValuePrice2/1000).toFixed(1) + " тыс";
                        $('#msg').html(msg1 + msg2);

                        return;
                       }

                    if(1000000 <= ValuePrice2 && ValuePrice2 < 1000000000) {

                          $('#msg').html('');
                          msg2 = ' - '+ (ValuePrice2/1000000).toFixed(1) + " млн";
                          $('#msg').html(msg1 + msg2);
                          return;
                         }
                     if( 1000000000 <= ValuePrice2 && ValuePrice2 < 100000000000) {

                          $('#msg').html('');
                           msg2 = ' - '+ (ValuePrice2/1000000000).toFixed(1) + " млрд";
                          $('#msg').html(msg1 + msg2);
                          return;
                         }
                      if( 100000000000 <= ValuePrice2) {

                          $('#msg').html('');
                           msg2 = "  ...";
                          $('#msg').html(msg1 + msg2);

                          return;
                         }



       }
    componentDidMount() {




  }

    renderSwitch(param) {
      switch(param) {
       case 'flat': return <NRooms rooms={rooms} />
       case 'house': return  <Houses houses={houses} />;
       case 'garage': return  <Garages garages={garages} />;
       case 'penthouse': return  <Penthouses penthouses={penthouses} />;
       case 'another': return  <Parts parts={parts} />;
       case 'commerce': return  <Commerce comercials ={comercials} />;
       default: null;
      }
    }
     render() {
     const state = this.state.value;
     let content1;
     const content = this.props.properties.map((property) =>
                    <option  value={property.value}>
                      {property.type}
                    </option>
                  );

     content1 = this.renderSwitch(state);







         return (
        <div class="form-row">
           <div class="col-md-4 mb-2">
               <select id="s1" class="form-control"   onChange={this.handleChange}>
                    {content}
               </select>
           </div>

           <div class="col-md-2  mb-2">
               <select class="form-control">
                   <option value="10" selected>{property.buy}</option>
                   <option value="11">{property.sale}</option>
                   <option value="12">{property.rent}</option>
               </select>
           </div>
           <div class="col-md-3 mb-2 ">

                {content1}

           </div>
           <div class="col-md-3 mb-2 ">
               <div class="dropdown">

                   <button id="msg" class="btn btn-light btn-block dropdown" type="button"
                          data-toggle="dropdown">{property.price}</button>


                   <div class="dropdown-menu w-150">
                       <div class="form-row">
                           <div class="form-group col">
                               <label>Min</label>
                               <input id="price" onKeyUp={this.handleKeyUpMin}  class="form-control" placeholder="0"
                                      min="0"  step="10000" type="number"/>
                           </div>
                           <div class="form-group  col">
                               <label>Max</label>
                               <input id="price2" onKeyUp={this.handleKeyUpMax}  class="form-control" placeholder="100000"
                                      min="0" step="10000" type="number"/>
                           </div>
                       </div>
                   </div>

               </div>
           </div>




        </div>

       );
    }
}

const properties = [
  {value: 'flat', type: property.flat},
  {value: 'flatnew', type: property.flatnew},
  {value: 'resale', type: property.resale},
  {value: 'apart', type: property.apart},
  {value: 'penthouse', type: property.penthouse},
  {value: 'another', type: property.another},
  {value: 'house', type: property.house},
  {value: 'garage', type: property.garage},
  {value: 'commerce', type: property.comerce},
];


const comercials = [
  {value: 20, type: comercial.office},
  {value: 21, type: comercial.shop},
  {value: 22, type: comercial.catering},
  {value: 23, type: comercial.storage},
  {value: 24, type: comercial.hotel},
  {value: 25, type: comercial.center},
  {value: 26, type: comercial.manuf},
  {value: 27, type: comercial.build},
  {value: 28, type: comercial.autoservis},
  {value: 29, type: comercial.buisnes},

];
const houses = [
  {value: 30, type: property.village},
  {value: 31, type: property.part},
  {value: 32, type: property.townhouse},
  {value: 33, type: property.land},
];

const garages = [
  {value: 40, type: property.gcomplex},
  {value: 41, type: property.parkplace},

];
const penthouses =[
   {value: 50, type: property.penthousebef200},
   {value: 51, type: property.penthousemo200},

];
const parts =[
   {value: 60, type: property.room},
   {value: 61, type: property.share},

];
const container = document.getElementById('root');
const root = ReactDOM.createRoot(container);
root.render(<MyForm properties={properties}/>);
