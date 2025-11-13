```bash
python -m venv myenv

myenv\Scripts\activate

    source venv/bin/activate

pip install flask requests
```

### server.py
```py
from flask import Flask, jsonify

app = Flask(__name__)

# Sample Employee Data
employees = {
    1: {"name": "Rahul Sharma", "designation": "Manager", "salary": 60000},
    2: {"name": "Sneha Patil", "designation": "Developer", "salary": 45000},
    3: {"name": "Amit Joshi", "designation": "Tester", "salary": 40000}
}

@app.route('/')
def home():
    return "Welcome to Employee Information Web Service"

# Get all employees
@app.route('/employees', methods=['GET'])
def get_all_employees():
    return jsonify(employees)

# Get one employee by ID
@app.route('/employees/<int:emp_id>', methods=['GET'])
def get_employee(emp_id):
    emp = employees.get(emp_id)
    if emp:
        return jsonify({emp_id: emp})
    else:
        return jsonify({"error": "Employee not found"}), 404

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)


```

### client.py
```py
import requests
import json

BASE_URL = "http://localhost:5000"

# 1. Get all employees
print("Fetching all employees:")
response = requests.get(f"{BASE_URL}/employees")
print(json.dumps(response.json(), indent=2))

# 2. Get a specific employee by ID
emp_id = 2
print(f"\nFetching details of employee with ID {emp_id}:")
response = requests.get(f"{BASE_URL}/employees/{emp_id}")
print(response.json())
```


```
python3 server.py
python3 client.py
```
