import { useNavigate, useParams } from 'react-router-dom';
import { Formik, Form, Field } from 'formik'
import {
    createNoteApi
} from './api/NoteApiService';

export default function NoteComponent() {

    const { id } = useParams();
    const navigate = useNavigate();

    
   

    const onSubmit = (values) => {
        console.log(values)
        const note = {
            description: values.description,
            todoId: Number(id)
        }
        console.log(note)

        createNoteApi(note)
                .then(response => {
                    navigate(`/todo/${id}/note`)
                })
                .catch(error => console.log(error))
    }

    return (
        <Formik initialValues={{ description: '' }}
            onSubmit={onSubmit} >
            {
                        (props) => (
                            <Form>
                                
                                <fieldset className="form-group">
                                    <label>Description</label>
                                    <Field type="text" className="form-control" name="description" />
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