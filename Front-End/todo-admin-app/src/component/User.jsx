import {
    List,
    Datagrid,
    TextField,
    EmailField,
    BooleanField,
} from "react-admin";



export const UserList = () => (
    <List>
        <Datagrid> 
            <TextField source="username"/>
            <EmailField source="email" />
            <BooleanField source="enabled"/>
        </Datagrid>
    </List>
);