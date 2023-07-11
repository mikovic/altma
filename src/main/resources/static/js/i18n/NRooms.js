export default class NRooms extends React.Component {
componentDidMount() {
   this.list = $("#my-multi");
   this.list.selectpicker();

  }

  render() {
  const content = this.props.rooms.map((room) =>
           <option  value={room.value}>
               {room.type}
            </option>
         );
  return(
   <div>
     <select id="my-multi" defaultValue ={['10', '11']}  title={Select} class="my-multi form-control" multiple >
        {content}
     </select>
   </div>
    );

  }
}
const rooms = [
  {value: 10, type: n1},
  {value: 11, type: n2},
  {value: 12, type: n3},
  {value: 13, type: n4}
];
