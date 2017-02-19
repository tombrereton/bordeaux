# Bordeaux
Group project for team Bordeaux at the University of Birmingham

## Description
*A online card game with a chat room and accessiblity features.*

---
**A good, simple, guide to git and github**:
http://rogerdudler.github.io/git-guide/

---
### Setting Up
- Install [git](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git]) 
- Clone the repository to some directory on your computer (commonly in your home directory so you don't have to `cd` a bunch of times). Do this by clicking the *clone or download* button to the top right and copying the https link and type the following in the terminal. 
```
git clone [repository clone link]
```
- Now we tell git where to upload and download the files (github repo) and we give it the name `upstream`. Replace `name` and `password` with your github details.
```
git remote add upstream https://name:password@github.org/repo.git
```
---
### Git Branch Model / Project Structure

- **Master**: Working program and where we submit our pull requests to.
- **Feature** branches: whatever feature you are currently working on.
- Ignore release and dev branches
![Alt description](http://nvie.com/img/git-model@2x.png)

---
### Creating your own branch and committing
- Github forks and branches are basically the same - to keep it simple we will just use branches.
- Create a branch for the feature you want to develop.
- Commit only to that branch while you develop that feature.
- As the master branch might be updated by someone else, we keep the program up to date by rebasing your branch before commiting. Rebasing means moving the orignal master commit you branched off of to the lastest master commit (this avoids major merge conflicts). 
- The image below shows the feature branch at its orignal position in grey, and then it is moved (rebased) so thats its base is now the latest master commit:

![Alt description](https://cms-assets.tutsplus.com/uploads/users/585/posts/23191/image/rebase.png)
- When the feature is complete, create a pull request on github so that branch will be pulled into the master branch.
- Pull requests means that someone can review the code before it is merged on github.
- We always work on our own branches (not master). Create your own branch with the  commands below. 
- https://github.com/Kunena/Kunena-Forum/wiki/Create-a-new-branch-with-git-and-manage-branches



Create a branch locally on your machine:
*Create the branch for whatever your working on - try to keep it small.*
```
git checkout -b [name_of_your_new_branch]
```

*Everytime* before working on your branch, rebase the branch so it's base is the latest master commit. This may introduce a merge conflict but will avoid HEAPS later. ([Rebase - pull request](https://github.com/edx/edx-platform/wiki/How-to-Rebase-a-Pull-Request))

```
git checkout master
git pull upstream master
git checkout [name_of_your_new_branch]
git rebase upstream/master
```

### Committing code to git and github

If you have created new files, add them to the "staging area" with: 
```
git add -A
```

Then commit them to your local git
```
git commit -m "[some message decribing what youve done so far]"
```


Then push (upload) your local branch to the github repo
```
git push upstream [name_of_your_new_branch]
```

But we need to make sure the branch is up to date with the master so really the commands should be:
```
git add -A
git commit -m "[some message decribing what youve done so far]"
git checkout master
git pull upstream master
git checkout [name_of_your_new_branch]
git rebase upstream/master
git push upstream [name_of_your_new_branch]
```
---
### Other useful git commands

```
# View all branches
git branch
```

```
# See current status
git status
```

```
# Retrieve your history
git log
```

```
# Reverse commit
git revert {SHA1}

# Amend commit
git commit --amend

# Uncommit
git reset --mixed HEAD file

# Discard changes
git checkout file

# Reset branch to a given state
git reset --hard ref
```

---
### Git Commit Messages
http://tbaggery.com/2008/04/19/a-note-about-git-commit-messages.html

Here’s an example of a good Git commit message:

```
Capitalized, short (50 chars or less) summary <- only really need this part

More detailed explanatory text, if necessary.  Wrap it to about 72
characters or so.  In some contexts, the first line is treated as the
subject of an email and the rest of the text as the body.  The blank
line separating the summary from the body is critical (unless you omit
the body entirely); tools like rebase can get confused if you run the
two together.

Write your commit message in the imperative: "Fix bug" and not "Fixed bug"
or "Fixes bug."  This convention matches up with commit messages generated
by commands like git merge and git revert.

Further paragraphs come after blank lines.

- Bullet points are okay, too

- Typically a hyphen or asterisk is used for the bullet, followed by a
  single space, with blank lines in between, but conventions vary here

- Use a hanging indent
```

---

### Resources
- https://illustrated-git.readthedocs.io/en/latest/
- https://git-scm.com/book/en/v2/Git-Branching-Basic-Branching-and-Merging
- https://git-scm.com/book/en/v2/Getting-Started-Git-Basics







