import { useState } from "react";
import {
    List,
    Datagrid,
    TextField,
    BooleanField,
    DateField,
    DateTimeInput,
    Create,
    SimpleForm,
    TextInput,
    ReferenceInput,
    SelectInput,
    useGetList,
    FormDataConsumer,
    EditButton,
    Edit,
    useRecordContext
} from "react-admin";

const TodoFilter = [
    <TextInput source="description" label="Search" alwaysOn />,
    <ReferenceInput source="category" recordRepresentation="name" label="Category" reference="category" />,
    <ReferenceInput source="user" label="User" reference="user" />
];
export const TodoList = () => (

    <List filters={TodoFilter}>
        <Datagrid>
            <TextField source="description" />
            <DateField source="createDate" />
            <DateField source="targetDate" />
            <BooleanField source="done" />
            <TextField source="category.name" label="Category" />
            <TextField source="user.username" label="User" />
            <EditButton />
        </Datagrid>
    </List>
);

export const TodoEdit = () => {
    const { data: choices, isLoading: isLoadingChoices } = useGetList('category');
    const { data: pickers, isLoading: isLoadingPicker } = useGetList('user');
    const [username, setUsername] = useState();
    
    const handleSelectUser = (event) => {
        setUsername(event.target.value);
    }

    const filterCategories = (category, username) => {
        return category?.filter(category => {
            return category?.user?.username === username;
        });
    }

    const useCategories = filterCategories(choices, username);
    return (
        <Edit>
            <SimpleForm>
                <TextInput source="description" />
                <DateTimeInput source="targetDate" />
                <SelectInput
                    source="user.username"
                    onChange={handleSelectUser}
                    choices={pickers}
                    optionText="username"
                    optionValue="username"
                    isLoading={isLoadingPicker}
                />
                <FormDataConsumer>
                    {
                        ({ formData }) => formData.user.username &&
                            <SelectInput
                                source="category.id"
                                choices={useCategories}
                                optionText="name"
                                optionValue="id"
                                isLoading={isLoadingChoices}
                            />
                    }
                </FormDataConsumer>
            </SimpleForm>
        </Edit>
    )
};
export const TodoCreate = () => {
    const { data: choices, isLoading: isLoadingChoices } = useGetList('category');
    const { data: pickers, isLoading: isLoadingPicker } = useGetList('user');
    const [username, setUsername] = useState('');

    const handleSelectUser = (event) => {
        setUsername(event.target.value);
    }

    const filterCategories = (category, username) => {
        return category?.filter(category => {
            return category?.user?.username === username;
        });
    }

    const useCategories = filterCategories(choices, username);



    return (
        <Create>
            <SimpleForm>
                <TextInput source="description" />
                <DateTimeInput source="targetDate" />
                <span>User:</span>
                <SelectInput
                    source="username"
                    onChange={handleSelectUser}
                    choices={pickers}
                    optionText="username"
                    optionValue="username"
                    isLoading={isLoadingPicker}
                />
                <FormDataConsumer>
                    {
                        ({ formData }) => formData.username &&
                            <SelectInput
                                source="catId"
                                choices={useCategories}
                                optionText="name"
                                optionValue="id"
                                isLoading={isLoadingChoices}
                            />
                    }
                </FormDataConsumer>
            </SimpleForm>
        </Create>
    )
};