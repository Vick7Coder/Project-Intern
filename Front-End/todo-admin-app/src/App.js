import * as React from "react";
import {fetchUtils,  Admin, Resource } from 'react-admin';
import simpleRestProvider from 'ra-data-simple-rest';
import {Welcome} from './component/Welcome'
import {authProvider} from './security/authProvider';
import { CategoryCreate, CategoryEdit, CategoryList } from "./component/Category";

const httpClient = (url, options = {}) => {
    if (!options.headers) {
        options.headers = new Headers({
          Accept: 'application/json'
        });
    }
    options.credentials = 'include';
    return fetchUtils.fetchJson(url, options);
}

const App = () => (
    <Admin dashboard={Welcome} authProvider={authProvider} requireAuth dataProvider={simpleRestProvider('http://localhost:8019/api/v2', httpClient)}>
        <Resource name="category" list={CategoryList} edit={CategoryEdit} create={CategoryCreate}/>
    </Admin>
);

export default App;