import { List, Datagrid } from "react-admin";
export const Note = () => (
    <List>
        <Datagrid rowClick="show">
            <TextField source="id" />
            <TextField source="name" />
            <TextField source="user.username"/>
        </Datagrid>
    </List>
);