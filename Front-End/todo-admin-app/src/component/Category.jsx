import {
    List,
    Datagrid,
    TextField,
    Edit,
    SimpleForm,
    Create,
    EditButton,
    TextInput,
    SelectInput,
    useGetList,
    BooleanInput,
    BooleanField
} from "react-admin";


export const UserFilter = () => {
    const { data: pickers, isLoading: isLoadingPicker } = useGetList('user');
    return (
        <SelectInput
            source="user"
            choices={pickers}
            optionText="username"
            optionValue="id"
            isLoading={isLoadingPicker}
        />
    )
};

const CategoryFilters = [
    <TextInput source="name" label="Search" alwaysOn />,
    <UserFilter label="User" alwaysOn/>,
    <BooleanInput source="enabled"/>
];

export const CategoryList = () => (
    <List filters={CategoryFilters} >
        <Datagrid>
            <TextField source="name" />
            <TextField source="user.username" />
            <BooleanField source="enabled"/>
            <EditButton />
        </Datagrid>
    </List>
);


export const CategoryEdit = () => (
    <Edit>
        <SimpleForm>
            <TextInput source="id" disabled />
            <TextInput source="name" />
            <BooleanInput source="enabled"/>
        </SimpleForm>
    </Edit>
);

export const CategoryCreate = () => {
    const {data: pickers, isLoading: isLoadingPicker} = useGetList('user');
    return (
        <Create>
            <SimpleForm>
                <TextInput source="name" />
                <span>User:</span>
                <SelectInput
                    source="username"
                    choices={pickers}
                    optionText="username"
                    optionValue="username"
                    isLoading={isLoadingPicker}
                />
            </SimpleForm>
        </Create>
    )
};
