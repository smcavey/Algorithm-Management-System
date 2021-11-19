# Group.Iteration.One
### Galatea | Dennis Juhasz, Spencer McAvey, Pooja Patel, Shannon Truong

# Algorithm Management System
### Notes: (1) Sample data is present on the webpage (2) The webpage will take a few seconds to load the data

## A. [GUI Landing Page](https://galateabucket.s3-external-1.amazonaws.com/html/index.html?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20211111T015015Z&X-Amz-SignedHeaders=host&X-Amz-Expires=604796&X-Amz-Credential=AKIAR4HIXYZIGVQ2FD67%2F20211111%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Signature=fd5e6735e46c9c7fa51924721d8aae202fa4263adab13bc18ed3a0e374c5f2d2)
1.  **Register user** - A user can register by submitting a 'Username' and 'Password' at the top of the webpage and then clicking 'Register'. The user would then need to use the Login form and click 'Login' using the 'Username' and 'Password' previously registered in order for the user to use the 'Create' features.
2.  **Create a top-level classification** - A registered user can then add a classification by clicking the 'Add Classification' button. To add a classification, a classification 'Name' and 'Description' are required, then the user must click 'Submit'.
3.  **Create an algorithm for a top-level classification** - A registered user can also add an algorithm by clicking the 'Add Algorithm' button. To add an algorithm, the 'Classification Name' of an existing classification that the algorithm will fall under,  'Algorithm Name' and 'Algorithm Description' are required, then the user must click 'Submit'.
4.  **Create an implementation for an algorithm** - A registered user can also add an implementation by clicking the 'Add Implementation' button. To add an implementation, the 'Classification Name' of an existing classification that the algorithm will fall under, 'Algorithm Name' of an existing algorithm, 'Implementation Language', 'Implementation Description' and a file upload of the implementation are required, then the user must click 'Submit'.

## B. Next Iteration
 - **Add problem instance**  - A button was added next to the other 'add' buttons. We intend to include a form to associated with that button to add a problem instance. The button is not yet functional, but would include a form with similar requisites as the other buttons.
- **Remove Classification** - A 'delete' icon is located next to the classification description where we intend to link that button to a delete classification method.
- **Remove Algorithm** - A 'delete' icon is located next to the algorithm description where we intend to link that button to a delete algorithm method.
- **Remove Implementation** A 'delete' icon is located next to the implementation description where we intend to link that button to a delete implementation method.

## C. Database Storage Tables
- The 'CREATE TABLE' statements are located in the .pdf included in the Group.Iteration.One submission
