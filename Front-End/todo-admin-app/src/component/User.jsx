import {
    List,
    Datagrid,
    TextField,
    EmailField,
    BooleanField,
    TextInput,
    useGetList,
    SelectInput
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
];


export const UserList = () => (
    <List filters={UFilter}>
        <Datagrid> 
            <TextField source="username"/>
            <EmailField source="email" />
            <BooleanField source="enabled"/>
        </Datagrid>
    </List>
);