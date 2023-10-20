import { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import { createTodoApi, retrieveTodoApi, updateTodoApi } from './api/TodoApiService'
import { Formik, Form, Field, ErrorMessage } from 'formik'
import { retrieveAllCategoryForUser } from './api/CategoryApiService'
import moment from 'moment/moment'

export default function TodoComponent() {

    const { id } = useParams()

    const [description, setDescription] = useState('')
    const [targetDate, setTargetDate] = useState('')
    const [categories, setCategories] = useState([])
    const [cateId, setCatId] = useState('')
    const navigate = useNavigate();

    useEffect(() => {
        if (id != -1) {
            retrieveTodoApi(id)
                .then(response => {
                    setDescription(response.data.description)
                    const formattedTargetDate = moment(response.data.targetDate).format('YYYY-MM-DDTHH:mm');
                    setTargetDate(formattedTargetDate);
                    if (response.data.category) {
                        setCatId(response.data.category.id);
                    }
                })
                .catch(error => console.log(error))
        }

        retrieveAllCategoryForUser()
            .then(response => setCategories(response.data))
            .catch(error => console.log(error))

    }, [id])
    function onSubmit(values) {
        if(!values.catId) {
            alert('Please select category');
            return;}
        const targetDateTime = new Date(values.targetDate);
        console.log(values)
        const todo = {
            id: parseInt(id),
            description: values.description,
            targetDate: targetDateTime,
            catId: values.catId
        }

        console.log(todo)

        if (id == -1) {
            createTodoApi(todo)
                .then(response => {
                    navigate('/todos')
                })
                .catch(error => console.log(error))

        }
        else {
            updateTodoApi(id, todo)
                .then(response => {
                    navigate('/todos')
                })
                .catch(error => console.log(error))
        }
    }

    function validate(values) {
        let errors = {}
        if (values.description.length < 5) {
            errors.description = 'Enter atleast 5 characters'
        }
        if (values.targetDate == '' || values.targetDate == null || !moment(values.targetDate).isValid) {
            errors.targetDate = 'Enter a target date!'
        }
        console.log(values)
        return errors
    }

    return (
        <div className="container">
            <h1>Enter Todo Details </h1>
            <div>
                <Formik initialValues={{ description: description || '', targetDate: targetDate || '', catId: cateId || '' }}
                    enableReinitialize={true}
                    onSubmit={onSubmit}
                    validate={validate}
                    validateOnChange={false}
                    validateOnBlur={false}


                >
                    {
                        (props) => (
                            <Form>
                                <ErrorMessage
                                    name="description"
                                    component="div"
                                    className="alert alert-warning"
                                />
                                <ErrorMessage
                                    name="targetDate"
                                    component="div"
                                    className="alert alert-warning"
                                />
                                <fieldset className="form-group">
                                    <label>Description</label>
                                    <Field type="text" className="form-control" name="description" />
                                </fieldset>
                                <fieldset className="form-group">
                                    <label>Target Date</label>
                                    <Field type="datetime-local" className="form-control" name="targetDate" />
                                </fieldset>
                                <fieldset className="form-group">
                                    <label>Category</label>
                                    <Field as="select" className="form-control" name="catId">
                                        <option>--Choice Category--</option>
                                        {categories.map(category => (
                                            <option
                                                key={category.id}
                                                value={category.id} // set value as id
                                            >
                                                {category.name}
                                            </option>
                                        ))}
                                    </Field>
                                </fieldset>
                                <div className="text-center">
                                    <button className="btn btn-dark custom-button m-5" type="submit">Save</button>
                                </div>
                            </Form>
                        )
                    }
                </Formik>
            </div>

        </div>
    )
}