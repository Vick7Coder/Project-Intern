import {
    List,
    Datagrid,
    TextField,
    Edit,
    SimpleForm,
    Create,
    EditButton,
    TextInput,
} from "react-admin";

const CategoryFilters = [
    <TextInput source="name" label="Search" alwaysOn />
];

export const CategoryList = () => (
    <List filters={CategoryFilters}>
        <Datagrid> 
            <TextField source="name" />
            <TextField source="user.username"/>
            <EditButton/>
        </Datagrid>
    </List>
);


export const CategoryEdit = () => (
    <Edit>
        <SimpleForm>
            <TextInput source="id"  disabled/>
            <TextInput source="name" />
        </SimpleForm>
    </Edit>
);

export const CategoryCreate = () => (
    <Create>
        <SimpleForm>
            <TextInput source="name" />
        </SimpleForm>
    </Create>
);
