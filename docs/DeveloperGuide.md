---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* {list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document `docs/diagrams` folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.
</div>

### Architecture

<img src="images/ArchitectureDiagram.png" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/AY2425S1-CS2103T-W09-2/tp/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2425S1-CS2103T-W09-2/tp/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2425S1-CS2103T-W09-2/tp/tree/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `StudentListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2425S1-CS2103T-W09-2/tp/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2425S1-CS2103T-W09-2/tp/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Student` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/AY2425S1-CS2103T-W09-2/tp/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</div>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `EdulogParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a student).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `EdulogParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `EdulogParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/AY2425S1-CS2103T-W09-2/tp/tree/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="450" />


The `Model` component,

* stores the edulog data i.e., all `Student` objects (which are contained in a `UniqueStudentList` object).
* stores the currently 'selected' `Student` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Student>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `Edulog`, which `Student` references. This allows `Edulog` to only require one `Tag` object per unique tag, instead of each `Student` needing their own `Tag` objects.<br>

<img src="images/BetterModelClassDiagram.png" width="450" />

</div>


### Storage component

**API** : [`Storage.java`](https://github.com/AY2425S1-CS2103T-W09-2/tp/tree/master/src/main/java/seedu/address/storage/Storage.java)

<img src="images/StorageClassDiagram.png" width="550" />

The `Storage` component,
* can save both edulog data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `EdulogStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.edulog.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedEdulog`. It extends `Edulog` with an undo/redo history, stored internally as an `eduLogStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedEdulog#commit()` — Saves the current edulog state in its history.
* `VersionedEdulog#undo()` — Restores the previous edulog state from its history.
* `VersionedEdulog#redo()` — Restores a previously undone edulog state from its history.

These operations are exposed in the `Model` interface as `Model#commitEdulog()`, `Model#undoEdulog()` and `Model#redoEdulog()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedEdulog` will be initialized with the initial edulog state, and the `currentStatePointer` pointing to that single edulog state.

![UndoRedoState0](images/UndoRedoState0.png)

Step 2. The user executes `delete 5` command to delete the 5th student in the edulog. The `delete` command calls `Model#commitEdulog()`, causing the modified state of the edulog after the `delete 5` command executes to be saved in the `eduLogStateList`, and the `currentStatePointer` is shifted to the newly inserted edulog state.

![UndoRedoState1](images/UndoRedoState1.png)

Step 3. The user executes `add n/David …​` to add a new student. The `add` command also calls `Model#commitEdulog()`, causing another modified edulog state to be saved into the `eduLogStateList`.

![UndoRedoState2](images/UndoRedoState2.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If a command fails its execution, it will not call `Model#commitEdulog()`, so the edulog state will not be saved into the `eduLogStateList`.

</div>

Step 4. The user now decides that adding the student was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoEdulog()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous edulog state, and restores the edulog to that state.

![UndoRedoState3](images/UndoRedoState3.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index 0, pointing to the initial Edulog state, then there are no previous Edulog states to restore. The `undo` command uses `Model#canUndoEdulog()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</div>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

![UndoSequenceDiagram](images/UndoSequenceDiagram-Logic.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</div>

Similarly, how an undo operation goes through the `Model` component is shown below:

![UndoSequenceDiagram](images/UndoSequenceDiagram-Model.png)

The `redo` command does the opposite — it calls `Model#redoEdulog()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the edulog to that state.

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index `eduLogStateList.size() - 1`, pointing to the latest edulog state, then there are no undone Edulog states to restore. The `redo` command uses `Model#canRedoEdulog()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</div>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the edulog, such as `list`, will usually not call `Model#commitEdulog()`, `Model#undoEdulog()` or `Model#redoEdulog()`. Thus, the `eduLogStateList` remains unchanged.

![UndoRedoState4](images/UndoRedoState4.png)

Step 6. The user executes `clear`, which calls `Model#commitEdulog()`. Since the `currentStatePointer` is not pointing at the end of the `eduLogStateList`, all edulog states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

![UndoRedoState5](images/UndoRedoState5.png)

The following activity diagram summarizes what happens when a user executes a new command:

<img src="images/CommitActivityDiagram.png" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire edulog.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the student being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_


--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* has a need to manage a significant number of contacts
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**: Include management of students and classes faster than a typical mouse/GUI driven app


### User stories

Priorities: Essential (must have), Novel (nice to have), Typical (unlikely to have)

| Priority | As a                   | I want to | So that I can |
| :---- |:-----------------------| :---- | :---- |
| Essential | Teacher                | track class schedules | I can schedule students |
| Essential | Teacher                | manage student parent's contact | I know who to contact and how to contact |
| Essential | Teacher                | manage student address | I will be able to mail the student things |
| Essential | Teacher                | manage student phone number | I know who to contact and how to contact |
| Essential | Teacher                | manage student email | I am able to send email attachments |
| Essential | Teacher                | tag attendance of students in class | I can monitor student engagement |
| Essential | Teacher                | overview of level of student attendance in classes |  |
| Essential | Teacher                | manage teaching materials | I can easily access and update resources for each lesson. |
| Essential | Teacher                | tag students based on how much help they need | I can give each student the appropriate amount of help |
| Essential | Teacher                | create student progress reports | I can communicate progress to parents effectively. |
| Essential | Teacher                | track student participation in class discussions | I can identify students who need encouragement to engage. |
| Novel | Teacher                | provide automated lesson plans | I can save time creating good lesson plans |
| Novel | Teacher                | conduct online assessments | I can monitor student understanding remotely. |
| Novel | Tuition Business Owner | manage staff schedules | classes are appropriately covered |
| Novel | Tuition Business Owner | monitor student enrollments | adjust course offerings accordingly. |
| Novel | Tuition Business Owner | track expenses | manage budget effectively |
| Novel | Tuition Business Owner | track revenue | manage budget effectively |
| Novel | Tuition Business Owner | view monthly financial reports | I have a clear understanding of the business's financial health. |
| Novel | Tuition Business Owner | integrate with a payroll system | I can manage staff salaries more efficiently. |
| Typical | Teacher                | Track how much students have paid | I know whether to collect the payment |
| Typical | Teacher                | track when students need to pay | I know whether to collect the payment |
| Typical | Teacher                | know what my student is scoring | I can adjust my teaching procedures |
| Typical | Teacher                | rank my students | I can adjust my teaching procedures |
| Typical | Teacher                | reminder for class schedule | I never miss or forget a class |
| Typical | Teacher                | allow assignment names to be tagged to classes |  |
| Typical | Teacher                | allow assignments to be tagged with deadlines | they are aware of upcoming tasks , and I know what i have assigned |
| Typical | Teacher                | allow tracking of deadlines in the class schedule | they are aware of upcoming tasks , and I know what i have assigned |
| Typical | Teacher                | be alerted if a student is not around for too long | I can monitor student engagement |
| Typical | Teacher                | track course progress | know when to review material |
| Typical | Teacher                | set reminders for student homework deadlines | I can ensure students submit work on time. |
| Typical | Teacher                | manage feedback from students | I can adjust my teaching approach based on student input. |

*{More to be added}*

# Use cases

(For all use cases below, the **System** is the `EduLog` and the **Actor** is the `Teacher`, unless specified otherwise)

## UC1: Add a class
* Postcondition: A class, with at least a name, date, and time is created

**MSS**

1.  Teacher initiates the process to add a new class in EduLog
2.  System provides the required fields for class information
3.  Teacher supplies the information
4.  System validates the provided information to ensure it meets any specified criteria (e.g., uniqueness).
5.  System confirms that class has been added
Use case ends.

### **Extension:**

* **2a. Teacher wants to abort the ‘add class’ process**

    * 2a1. Teacher can clear fields and exit the procedure

* **4a. Class with description already exists**

    * 4a1. System alerts the teacher that the class exists and displays its details

* **4b. Invalid class details (see features)**

    * 4b1. System alerts the teacher and prompts them to correct the invalid information


## UC2: Delete a class

* Postcondition: An existing class is deleted

**MSS**

1.  Teacher initiates the process to delete a new class in EduLog
2.  System provides the required fields for class information
3.  Teacher supplies the information
4.  System validates the provided information to ensure it meets any specified criteria.
5.  System confirms that class has been deleted
Use case ends.

### **Extension:**

* **2a. Teacher wants to abort the ‘delete class’ process**

    * 2a1. Teacher can clear fields and exit the procedure

* **4a. Class with description does not exist**

    * 4a1. System alerts the teacher that the class does not exist, and prompts user to check again

* **4b. Invalid class details (see features)**

    * 4b1. System alerts the teacher and prompts them to correct the invalid information

## UC3: Add student

**System**: EduLog
**Actor**: Teacher 
**Postcondition**: A student, with at least a name, is successfully enrolled in at least one class

**MSS:**

1. Teacher initiates the process to add a new student in EduLog.
2. System provides the required fields for student information.
3. Teacher supplies the information.
4. System validates the provided information to ensure it meets any specified criteria (e.g., uniqueness).
5. System confirms that the student has been added.
Use case ends.

### **Extension:**

- **2a. Teacher wants to abort the ‘add student’ process**
  Teacher can clear fields and exit the procedure.

- **4a. Student with the same name already exists**
  System alerts the teacher that the student exists and displays their details.

- **4b. Invalid student details**
  System alerts the teacher and prompts them to correct the invalid information.

- **4c. Subject does not exist**
  System prompts the teacher to first create the tag using <u>UC7: Create a Subject</u>.

- **4d. Class does not exist**
  System prompts the teacher to first create the class using <u>UC1: Add a Class</u>.

- **4e. Tag does not exist**
  System prompts the teacher to first create the tag using <u>UC5: Create a Tag</u>.

## UC4: Edit student

**System**: EduLog
**Actor**: Teacher 

**MSS:**
1. Teacher initiates the process to edit an existing student in EduLog.
2. System provides the required fields for student information.
3. Teacher supplies the updated information.
4. System validates the provided information to ensure it meets any specified criteria (e.g., uniqueness).
5. System confirms that the student has been successfully updated.
Use case ends.

### **Extension:**
- **2a. Teacher wants to abort the ‘edit student’ process:**
  Teacher can clear fields and exit the procedure.

- **4a. Student with new name already exists**
  System alerts the teacher that the student exists and displays their details.

- **4b. Invalid student details (see features):**
  System alerts the teacher and prompts them to correct the invalid information.

- **4c. Subject does not exist**
  System prompts the teacher to first create the tag using <u>UC7: Create a Subject</u>.

- **4d. Class does not exist**
  System prompts the teacher to first create the class using <u>UC1: Add a Class</u>.

- **4e. Tag does not exist**
  System prompts the teacher to first create the tag using <u>UC5: Create a Tag</u>.

## UC5: Create Tag

**System**: EduLog
**Actor**: Teacher

**MSS:**
1. Teacher initiates the process to create a new tag.
2. System displays the required fields for tag creation.
3. Teacher supplies the necessary information for the new tag.
4. System validates the provided information to ensure it meets specified criteria.
5. System confirms that the tag has been successfully created and is available for use.
Use case ends.

### **Extension:**
- **2a. Teacher wants to abort the ‘create tag’ process:**
  Teacher clears the fields and exits the procedure without saving any data.

- **3a. Tag with the same name already exists**
  System alerts the teacher that the tag exists and no new tag is created.

- **3b. Invalid tag details:**
  System alerts the teacher if any of the entered details are invalid.
  System prompts the teacher to correct the information before proceeding.

## UC6: Edit tag

**System**: EduLog
**Actor**: Teacher

**MSS:**
1. Teacher initiates the process to edit an existing tag.
2. System displays the required fields for tag editing.
3. Teacher supplies the necessary information for the updated tag.
4. System validates the provided information to ensure it meets specified criteria.
5. System confirms that the tag has been successfully updated and is available for use.
Use case ends.

### **Extension:**
- **2a. Teacher wants to abort the ‘edit tag’ process:**
  Teacher clears the fields and exits the procedure without saving any data.

- **3a. Tag with new name already exists**
  System alerts the teacher that the tag exists.  
  System requests to add another name.

- **3b. Invalid tag details (see features):**
  System alerts the teacher if any of the entered details are invalid.  
  System prompts the teacher to correct the information before proceeding.

## UC7: Delete tag

**System**: EduLog
**Actor**: Teacher

**MSS:**

1. Teacher selects a tag to delete.
2. System prompts the teacher for confirmation before permanently deleting the tag.
3. System deletes the tag.
Use case ends.

### **Extension:**

- **1a. Tag does not exist**
  System alerts the teacher that the tag does not exist and cannot be deleted.

- **2a. Teacher wants to abort the ‘delete tag’ process:**
  Teacher cancels the operation and exits the procedure without deleting the tag.

## UC8: View students (Nikhil)

**System:** EduLog
**Actor:** Teacher  
**Precondition:** The teacher is logged into the app.

**MSS:**

1. Teacher requests to see students enrolled under him/her.  
2. System displays the students enrolled under him/her.
Use case ends.

### **Extension:**

**1a.** **Teacher requested to view invalid student**

**1a1.** System notifies the teacher that the student is not present in her set of students.

**1a2.** System requests the teacher for the correct student name.

Steps 1a1-1a2 are repeated until an existing student is selected.

Use case resumes from Step 2.

## UC9: Remove student (Nikhil)

**System:** EduLog
**Actor:** Teacher
**Precondition:** The teacher is logged into the app, the student is present in the list of students enrolled under the teacher.
**Postcondition:** The student is removed from the teacher’s list of students.

**MSS:**

1. Teacher views the students enrolled under him/her through U1. View Students
2. Teacher selects a student to delete.
3. Teacher requests to delete the student.
4. System confirms that the student has been removed.
   Teacher repeats steps 3-5 for any remaining students they wish to remove.
   Use case ends.

### **Extension:**

**3a.** **Student no longer exists in the list of the teacher’s students:**

**3a1.** System notifies the teacher that the student is not present in her set of students.

Use case ends.



### Non-Functional Requirements

1. Edulog should work on any mainstream OS, as long as the system has `java 17 jdk` installed.
2. The app should work on any mainstream computer architecture.
3. The app should also work on 32-bit and 64-bit architecture.
4.  Any command inputted by the user must be resolved within 1 second.
5. The app should be able to store 1000 student entries without any noticeable lag.
6. The app should not need any additional installation steps to run. The user only needs to run `java -jar Edulog.jar` to run the app.
7. The app should work without internet connection.
8. The app should work in devices with at least 4 GB of RAM and 64G of storage.
9. The app should take up at most 100MB of space, but this does not include user data.
10. The user needs only a keyboard to utilise all functionalities of the app.
11. Any external libraries used must be open sourced.
12. The app must not use a DBMS to store user data.
13. The GUI must work well (i.e everything displayed appropriately) for screen resolutions of 1920X1080 and higher.



### Glossary

1. Mainstream OS: Ubuntu, Mint, Windows, MacOS
2. Mainstream computer architecture: x86\_64, arm64
3. Java 17 jdk: The java 17 development kit, which runs a java 17 program in a virtual environment
4. CLI: command line interface
5. MSS: Main success scenario
6. 32-bit, 64-bit: refers to how many bits a cpu can process
7. RAM: random access memory
8. DBMS: Database management system
9. GUI: Graphic user interface
--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a student

1. Deleting a student while all students are being shown

   1. Prerequisites: List all students using the `list` command. Multiple students in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No student is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
