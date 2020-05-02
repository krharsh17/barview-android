# Contributing Guidelines

### Submitting Feedback, Requests, and Bugs

The process for submitting feedback, feature requests, and reporting bugs usually begins by discussion on [the community chat](https://gitter.im/barview-android/community) and, after initial clarification, through [GitHub issues](https://help.github.com/articles/about-issues/).

### Submitting Code and Documentation Changes

The process for accepting changes operates by [Pull Request (PR)](https://help.github.com/articles/about-pull-requests/) and has a few steps:

1.  If you haven't submitted anything before, and you aren't (yet!) a collaborator on the project, **fork and clone** the repo:

        $ git clone https://github.com/<username>/barview-android.git

2.  Create a **new branch** for the changes you want to work on. Choose a topic for your branch name that reflects the change:

        $ git checkout -b <branch-name>

3.  **Create or modify the files** with your changes. If you want to show other people work that isn't ready to merge in, commit your changes then create a pull request (PR) with _WIP_ or _Work In Progress_ in the title.

        https://github.com/krharsh17/barview-android/pull/new/master

4.  Once your changes are ready for final review, commit your changes then modify or **create your pull request (PR)**, assign as a reviewer or ping (using "`@<username>`") a mentor/maintainer (someone able to merge in PRs) active on the project

5.  Allow others sufficient **time for review and comments** before merging. We make use of GitHub's review feature to comment in-line on PRs when possible. There may be some fixes or adjustments you'll have to make based on feedback.

6.  Once you have integrated comments, or waited for feedback, a maintainer should merge your changes in!

### Branching

Our branching strategy is based on [this article](https://nvie.com/posts/a-successful-git-branching-model/) which I suggest you read.

+  **master** a history of releases, once merged to from develop and tagged we create a release on the play store & GitHub releases.

+  **develop**  the actively worked on next release of the app, what we branch off of while working on new features and what we merge into upon feature completion

+ **feature/** or feature/\<username\>/ any branch under this directory is an actively developed feature, feature branches culminate in a PR, are merged and deleted.
 Typically a feature branch is off of develop and into develop but in rare scenarios if there is an issue in production a branch may be made off master to fix this issue, this type of feature branch must be merged to develop and master before being deleted.
Branch names should be in the format **\<issue-number\>-kebab-case-title**

All branches should have distinct history and should be visually easy to follow, for this reason only perform merge commits when merging code either by PR or when synchronising.

If you wish to rebase you should be following the [Golden Rule](https://www.atlassian.com/git/tutorials/merging-vs-rebasing#the-golden-rule-of-rebasing) and ahere to the advice in the heading [Aside: Rebase as cleanup is awesome in the coding lifecycle](https://www.atlassian.com/git/articles/git-team-workflows-merge-or-rebase).

### Building

The BarView project is split into 2 modules
1. `app` - a sample app to test the barview library during development
1. `library` - the main library barview, which gets deployed to end user applications

The default build is `debug`, with this variant you can use a debugger while developing. To install the application click the `run` button in Android Studio with the `app` configuration selected while you have a device connected.

### Linting

PR should be linted properly locally. There is no system restriction applied for this, however, PRs will not be merged until they contain properly formatted code.

### Continous Integration

All PRs will have all these tests run and a combined coverage report will be attached, if coverage is to go down the PR will be marked failed. On Travis CI the automated tests are run on an emulator. To
learn more about the commands run on the CI please refer to [.travis.yml](https://github.com/krharsh17/barview-android/blob/develop/.travis.yml)

### Hope you have a wonderful experience contributing to the project 🎉
