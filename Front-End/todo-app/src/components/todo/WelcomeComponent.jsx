import {Link} from 'react-router-dom'

function WelcomeComponent() {
    

    return (
        <div className="Welcome">
            <h1>Welcome, Start your own journey! </h1>
            <div>
               Manage your todos - <Link to="/todos" className='link-body-emphasis link-offset-2 link-underline-opacity-25 link-underline-opacity-75-hover'>Go here!</Link>
            </div>
            <div>
                <a className='btn bg-transparent' style={{display: 'inline-block', width: 'auto'}}>
                    @Thank4Using
                </a>
            </div>
        </div>
    )
}

export default WelcomeComponent