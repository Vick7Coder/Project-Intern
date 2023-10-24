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
    useGetList
} from "react-admin";

const CategoryFilters = [
    <TextInput source="name" label="Search" alwaysOn />
];

export const CategoryList = () => (
    <List filters={CategoryFilters}>
        <Datagrid>
            <TextField source="name" />
            <TextField source="user.username" />
            <EditButton />
        </Datagrid>
    </List>
);


export const CategoryEdit = () => (
    <Edit>
        <SimpleForm>
            <TextInput source="id" disabled />
            <TextInput source="name" />
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
