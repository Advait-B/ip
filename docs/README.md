# Mango User Guide

Mango is a command-line task management application that helps users manage and track tasks efficiently. It supports todos, deadlines, events, listing, marking/unmarking, deleting, finding tasks, and automatic saving.

**Requires Java 17 or above.**

---

## Getting Started

### Application Setup

1. Download the latest `mango.jar` from this repository’s Releases page.
2. Place the JAR file in an empty folder.
3. Open a terminal in that folder.
4. Run:

```bash
java -jar mango.jar
```

Mango will display a greeting message.

5. You can begin entering commands immediately.

---

### Data Storage

Mango saves tasks to:

    ./data/mango.txt

The `data/` folder and `mango.txt` file are created automatically on first run.

Tasks are saved automatically.

---

### Command Rules

- Commands are case-insensitive.
- Extra spaces before commands are ignored.
- Task indices must be positive integers.
- `/by`, `/from`, and `/to` must be included exactly as shown for deadlines and events.
- Invalid inputs will result in an error message.

---

## Features

---

### `todo DESCRIPTION`

Adds a simple task without date or time.

#### Example

    todo finish tutorial

#### Output

    ------------------------------
    got it. i've added this task:
      [T][ ] finish tutorial
    now you have 1 task(s) in the list.
    ------------------------------

#### Error Cases

- Missing description

---

### `deadline DESCRIPTION /by DATE`

Adds a deadline task.

#### Example

    deadline submit assignment /by Friday

#### Output

    ------------------------------
    got it. i've added this task:
      [D][ ] submit assignment (by: Friday)
    now you have 2 task(s) in the list.
    ------------------------------

#### Error Cases

- Missing `/by`
- Missing description
- Missing date

---

### `event DESCRIPTION /from START /to END`

Adds an event.

#### Example

    event project meeting /from 2pm /to 4pm

#### Output

    ------------------------------
    got it. i've added this task:
      [E][ ] project meeting (from: 2pm to: 4pm)
    now you have 3 task(s) in the list.
    ------------------------------

#### Error Cases

- Missing `/from`
- Missing `/to`
- Missing description
- Missing start time
- Missing end time

---

### `list`

Displays all tasks.

#### Example

    list

#### Output

    ------------------------------
    oh dear, you have 3 task(s) you must complete.
    here's your task list:
    1. [T][ ] finish tutorial
    2. [D][ ] submit assignment (by: Friday)
    3. [E][ ] project meeting (from: 2pm to: 4pm)
    ------------------------------

---

### `mark INDEX`

Marks the task as done.

#### Example

    mark 2

#### Output

    ------------------------------
    hooray! you've completed this task. i've marked it as done:
    [D][X] submit assignment (by: Friday)
    ------------------------------

#### Error Cases

- Index is not a number
- Index is out of range
- Task is already marked

---

### `unmark INDEX`

Marks the task as not done.

#### Example

    unmark 2

#### Output

    ------------------------------
    oh dear, it seems you have not completed this task. i've unmarked it:
    [D][ ] submit assignment (by: Friday)
    ------------------------------

#### Error Cases

- Index is not a number
- Index is out of range
- Task is already unmarked

---

### `delete INDEX`

Deletes a task.

#### Example

    delete 2

#### Output

    ------------------------------
    noted. i've removed this task:
      [D][ ] submit assignment (by: Friday)
    now you have 2 task(s) in the list.
    ------------------------------

#### Error Cases

- Index is not a number
- Index is out of range

---

### `find KEYWORD`

Displays tasks containing the keyword.

#### Example

    find assignment

#### Output

    ------------------------------
    looking for the matching tasks in your list:
    1. [D][ ] submit assignment (by: Friday)
    ------------------------------

#### Error Cases

- Missing keyword

---

### `bye`

Exits Mango.

#### Output

    ------------------------------
    bye! hope to see you again!
    ------------------------------

---

## Example Session

    hey there, i'm mango!
    how can i help?
    ------------------------------

    todo clean room
    ------------------------------
    got it. i've added this task:
      [T][ ] clean room
    now you have 1 task(s) in the list.
    ------------------------------

    deadline finish cs2113 quiz /by tuesday night
    ------------------------------
    got it. i've added this task:
      [D][ ] finish cs2113 quiz (by: tuesday night)
    now you have 2 task(s) in the list.
    ------------------------------

    event attend cs2113 tutorial /from thursday 9am /to 10am
    ------------------------------
    got it. i've added this task:
      [E][ ] attend cs2113 tutorial (from: thursday 9am to: 10am)
    now you have 3 task(s) in the list.
    ------------------------------

    list
    ------------------------------
    oh dear, you have 3 task(s) you must complete.
    here's your task list:
    1. [T][ ] clean room
    2. [D][ ] finish cs2113 quiz (by: tuesday night)
    3. [E][ ] attend cs2113 tutorial (from: thursday 9am to: 10am)
    ------------------------------

    mark 1
    ------------------------------
    hooray! you've completed this task. i've marked it as done:
      [T][X] clean room
    ------------------------------

    find room
    ------------------------------
    looking for the matching tasks in your list:
    1. [T][X] clean room
    ------------------------------

    bye
    ------------------------------
    bye! hope to see you again!
    ------------------------------

---

## Command Summary

| Action | Format |
|--------|--------|
| Add Todo | `todo DESCRIPTION` |
| Add Deadline | `deadline DESCRIPTION /by DATE` |
| Add Event | `event DESCRIPTION /from START /to END` |
| List Tasks | `list` |
| Mark Task | `mark INDEX` |
| Unmark Task | `unmark INDEX` |
| Delete Task | `delete INDEX` |
| Find Tasks | `find KEYWORD` |
| Exit | `bye` |