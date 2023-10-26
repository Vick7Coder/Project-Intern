import { useState } from "react";
import { Box, Typography } from '@mui/material';
import { useParams } from 'react-router-dom'
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
    SelectInput,
    useGetList,
    FormDataConsumer,
    EditButton,
    Edit,
    useGetOne,
    useNotify,
    useRedirect,
    CreateButton
} from "react-admin";
export const CateFilter = () => {
    const { data: choices, isLoading: isLoadingChoices } = useGetList('category');
    return (
        <SelectInput
            source="category"
            choices={choices}
            optionText="name"
            optionValue="id"
            isLoading={isLoadingChoices}
        />
    )
};
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

const TodoFilter = [
    <TextInput source="description" label="Search" alwaysOn />,
    <CateFilter label="Category" alwaysOn />,
    <UserFilter label="User" alwaysOn />,

];
const Empty = () => (
    <Box textAlign="center" m={1}>
        <Typography variant="h4" paragraph>
            No Todo 
        </Typography>
        <Typography variant="body1">
            Create one or import from a file
        </Typography>
        <CreateButton />
    </Box>
);
export const TodoList = () => (

    <List sort={{ field: 'targetDate', order: 'ASC' }} filters={TodoFilter}>
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
    const notify = useNotify();
    const redirect = useRedirect();
    const onSuccess = () => {
        notify('Todo Updated');
        redirect('/todo');
    };
    const { data: choices, isLoading: isLoadingChoices } = useGetList('category');
    const { data: pickers, isLoading: isLoadingPicker } = useGetList('user');
    const { id } = useParams();
    const data = useGetOne('todo', { id: id });
    const td = data;
    const [username, setUsername] = useState(td.data.user.username);

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
        <Edit onSuccess={onSuccess}>
            <SimpleForm>
                <TextInput source="description" />
                <DateTimeInput source="targetDate" />
                <SelectInput
                    label="Username"
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
                                label="Category"
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
    const notify = useNotify();
    const redirect = useRedirect();
    const onSuccess = () => {
        notify('Todo created');
        redirect('/todo');
    };
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
        <Create mutationOptions={{ onSuccess }}>
            <SimpleForm>
                <TextInput source="description" />
                <DateTimeInput source="targetDate" />
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