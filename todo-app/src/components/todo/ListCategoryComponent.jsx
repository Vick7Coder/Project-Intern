import { useEffect, useState } from "react";
import { deleteCategoryApi, enableCategoryApi, retrieveAllCategoryForUser } from "./api/CategoryApiService";
import { useAuth } from "./security/AuthContext";
import { useNavigate } from "react-router-dom";

function ListCategoryComponent() {

    const [categories, setCategories] = useState([])
    const [message, setMessage] = useState(null)
    const authContext = useAuth()
    const navigate = useNavigate();
    const username = authContext.username

    useEffect(() => refreshCategories(), [])

    function refreshCategories() {
        retrieveAllCategoryForUser(username)
            .then(response => {
                setCategories(response.data)
            })
            .catch(error => console.log(error))

    }

    function deleteCategory(id) {
        deleteCategoryApi(id)
            .then(() => {
                setMessage(`Deletion of category with ID = ${id} was successful!`)
                refreshCategories()
            })
            .catch(error => console.log(error))

    }

    function enableCategory(id){
        enableCategoryApi(id)
            .then(() => {
                setMessage(`Deletion of category with ID = ${id} was successful!`)
                refreshCategories()
            })
            .catch(error => console.log(error))
        
    }

    function updateCategory(id) {
        navigate(`/category/${id}`)

    }

    function addNewCategory() {
        navigate(`/category/-1`)
    }
    function todoCategory(id){
        navigate(`/category/${id}/todo`)
    }

    return (
        <div className="container">
            <h1>Category!</h1>
            {message && <div className="alert alert-warning">{message}</div>}
            <table className='table'>
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Delete</th>
                        <th>Update</th>
                        <th>Todo List</th>

                    </tr>
                </thead>

                <tbody>
                    {
                        categories.map(category => (
                            <tr key={category.id}>
                                <td>{category.name}</td>
                                <td>
                                    <button
                                        className="btn btn-dark custom-button"
                                        onClick={() => enableCategory(category.id)}
                                    >
                                        Delete
                                    </button>
                                </td>
                                <td>
                                    <button
                                        className="btn btn-light custom-button"
                                        onClick={() => updateCategory(category.id)}
                                    >
                                        Update
                                    </button>
                                </td>
                                <td>
                                    <button
                                        className="btn btn-light custom-button"
                                        onClick={() => todoCategory(category.id)}
                                    >
                                        View
                                    </button>
                                </td>
                            </tr>
                        ))
                    }
                </tbody>
            </table>

            <button
                type="button"
                className="btn btn-secondary btn-lg custom-button m-5"
                onClick={addNewCategory}
            >
                Add New Category
            </button>

        </div>
    )
}

export default ListCategoryComponent