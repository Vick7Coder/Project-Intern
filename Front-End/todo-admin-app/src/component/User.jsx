import {
    List,
    Datagrid,
    TextField,
    EmailField,
    BooleanField,
    TextInput,
    useGetList,
    SelectInput,
    BooleanInput,
    Edit,
    SimpleForm,
    EditButton
} from "react-admin";


export const UserFilter = () => {
    const { data: pickers, isLoading: isLoadingPicker } = useGetList('user');
    return (
        <SelectInput
            source="username"
            choices={pickers}
            optionText="username"
            optionValue="username"
            isLoading={isLoadingPicker}
        />
    )
};
const UFilter = [
    <TextInput source="email" label="Email" alwaysOn />,
    <UserFilter label="Username" alwaysOn />,
    <BooleanInput source="enabled"/>
];


export const UserList = () => (
    <List filters={UFilter}>
        <Datagrid> 
            <TextField source="username"/>
            <EmailField source="email" />
            <BooleanField source="enabled"/>
            <EditButton/>
        </Datagrid>
    </List>
);

export const UserEdit = () => (
    <Edit>
        <SimpleForm>
            <TextInput source="username" disabled />
            <TextInput source="email" disabled />
            <BooleanInput source="enabled"/>
        </SimpleForm>
    </Edit>
);
