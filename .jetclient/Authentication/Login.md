```toml
name = 'Login'
method = 'POST'
url = 'http://localhost:8080/api/auth/login'
sortWeight = 1000000
id = 'eaad34de-8bba-48ff-8172-5b7c15a20f31'

[[headers]]
key = 'Content-Type'
value = 'application/json'

[body]
type = 'JSON'
raw = '''
{
    "username": "defaultuser",
    "password": "home"

}'''
```
