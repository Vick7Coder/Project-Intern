import * as React from "react";
import { fetchUtils, Admin, Resource } from 'react-admin';
import simpleRestProvider from 'ra-data-simple-rest';
import { Welcome } from './component/Welcome'
import { authProvider } from './security/authProvider';
import { CategoryCreate, CategoryEdit, CategoryList } from "./component/Category";
import { UserEdit, UserList } from "./component/User";
import { TodoCreate, TodoEdit, TodoList } from "./component/Todo";
import ListAltIcon from '@mui/icons-material/ListAlt';
import CategoryIcon from '@mui/icons-material/Category';
import AssignmentIndIcon from '@mui/icons-material/AssignmentInd';
const httpClient = (url, options = {}) => {
    if (!options.headers) {
        options.headers = new Headers({
            Accept: 'application/json'
        });
    }
    options.credentials = 'include';
    return fetchUtils.fetchJson(url, options);
}

const App = () => {
    return (
        <Admin dashboard={Welcome} authProvider={authProvider} requireAuth dataProvider={simpleRestProvider('http://localhost:8019/api/v2', httpClient)}>
            <Resource name="category" list={CategoryList} edit={CategoryEdit} create={CategoryCreate} icon={CategoryIcon}/>
            <Resource name="user" edit={UserEdit} list={UserList} icon={AssignmentIndIcon}/>
            <Resource name="todo" list={TodoList} create={TodoCreate} edit={TodoEdit}  icon={ListAltIcon}/>
        </Admin>
    )
};

export default App;