import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { Formik, Form, Field } from 'formik'

import {
    retrieveCategoryApi,
    updateCategoryApi,
    createCategoryApi
} from './api/CategoryApiService';

export default function CategoryComponent() {

    const { id } = useParams();
    const [name, setName] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        if (id) {
            getCategory();
        
        }
    }, [id]);

    const getCategory = () => {
       if(id != -1){
        retrieveCategoryApi(id)
            .then(response => setName(response.data.name))
            .catch(error => console.log(error))
       }
    }


    const onSubmit = (values) => {
        const category = {
            id,
            name: values.name,
        }

        if (id == -1) {
            createCategoryApi(category)
                .then(response => {
                    navigate('/category')
                })
                .catch(error => console.log(error))
            
        } else {
            updateCategoryApi(id, category)
                .then(response => {
                    navigate('/category')
                })
                .catch(error => console.log(error))
        }
    }

    if(id!=-1){
        return (
            <Formik initialValues={{ name }}
                enableReinitialize={true}
                onSubmit={onSubmit} >
                {
                            (props) => (
                                <Form>
                                    
                                    <fieldset className="form-group">
                                        <label>Description</label>
                                        <Field type="text" className="form-control" name="name" />
                                    </fieldset>
                                    
                                    <div className="text-center">
                                        <button className="btn btn-dark custom-button m-5" type="submit">Save</button>
                                    </div>
                                </Form>
                            )
                        }
            </Formik>
        )
    }
    else{
        return(
            <Formik initialValues={{ name }}
                enableReinitialize={true}
                onSubmit={onSubmit} >
                {
                            (props) => (
                                <Form>
                                    
                                    <fieldset className="form-group">
                                        <label>Description</label>
                                        <Field type="text" className="form-control" name="name" />
                                    </fieldset>
                                    
                                    <div className="text-center">
                                        <button className="btn btn-dark custom-button m-5" type="submit">Save</button>
                                    </div>
                                </Form>
                            )
                        }
            </Formik>
            
        )
    }
}