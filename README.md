# StackOverflow

Dec 9, 2018: 
- Basic app to list questions and answers from stackoverflow.
 Lib: 
    1. Retrofit
    2. Gson

Dec 16, 2018: 
- Move networking and dialogue management logic to separate classes.
- Refactor to a MVP pattern architecture.

Dec 17, 2018:
- Create CompositionRoot to hold Application level dependencies.
- Create PresentationCompositionRoot to hold Activity level dependencies.

Dec 19, 2018:
- Added some new UI. Show avatar of the user who asked the question.
 Lib:
  1. Glide
  
Dec 24, 2018:
- Adding Dagger dependency Injection
- Composition CompositionRoot using Dagger
Lib:
  1. Dagger 2  

Dec 25, 2018:
- Converting PresentationCompositionRoot to a DaggerComponent (PresentationComponent).
- Providing ApplicationComponent as dependency to PresentationComponent.

Jan 1, 2019,
 - Using Subcomponent to resolve dependency ov PresentationComponent on AppComponent.
