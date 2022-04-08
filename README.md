# Mojo Application
The Mojo Application takes a JSON layout file and renderes those layouts via an Android custom View.

- [Package Structure](#package-structure)
   * [data](#data)
   * [render](#render)
   * [engine](#engine)
   * [di](#di)
- [From JSON to onDraw](#from-json-to-ondraw)
- [Screenshot Tests](#screenshot-tests)
- [Unit Tests](#unit-tests)

# Package Structure
The Project contains the following package structure:
## data
The data package holds:
- Layout. The Layout is the kotlin representation of the JSON object.
- LayoutFetcher. The LayoutFetcher is responsible to fetch & parse the JSON string.

## render
The render package contains only the `MojoView` class.

`MojoView` is a lightweight Android View. `MojoView` renders the `List<RenderObject>` onto the screen. `MojoView` simply iterates the `List<RenderObject>` and calls `drawBackground` or `drawBitmap`. 
To keep the `MojoView` lightweight the `List<RenderObject>` has all positions/bounds, colors, and bitmaps pre-calculated.

## engine
The engine package contains the classes related to converting/adapting the Layout into a List<RenderObect>. The engine package includes the following class's:

- `LayoutAdapter`, follows the Adapter pattern to adapt/convert a Layout object into a List<RenderObect>. Before we render the Layout objects on screen, we need to calculate the positions and bounds for the Layout. The `LayoutAdapter` calculates the positions, bounds, and fetches media for the Layout and flattens the Layout into a `List<RenderObject>`. After `LayoutAdapter` pre-calculates positions, bounds, and media, the `List<RenderObject>` can render onto the `MojoView`.
- `media.MediaAdapter`, is the same as LayoutManager, only it takes care of the media field on Layout. The media field tells the Adapter that the `RenderObject` needs to include an image. The Layout media fields adapt to the MediaObject. Picasso is used to fetch images, scale and fit/fill those images in the pre-calculated bounds.
- `media.ImageFetcher`, follows the Builder pattern and is a wrapper on Picasso builder functions. `ImageFetcher` wraps Picasso to enable unit-testing the `MediaAdapter`
- `RenderObject` and `MediaObject` contain the bounds, color, and bitmap that the `MojoView` uses to draw/render the objects on the screen.
Anchor and Padding contain the calculations for the bounds depending on the anchor_x/anchor_y, width/height, and padding of the Layout.

## di
The di(Dependency Injection) package contains:
- `AppDispatchers`. We use coroutines so that Picasso loads images on the background thread. In Screenshot tests, we can switch the background IO thread for image loading. We'll later see how Espresso tests need to wait for Picasso before asserting if the image is on screen.
- `LayoutFetcherLocator` follows the Locator pattern. In Screenshot and Unit tests, we need to swap the implementation details of `LayoutFetcher` so that in tests, we load the layout JSON files from a different location than the real app.
- `MojoApplication`, is the Android Application class. `MojoApplication` implements `Injector` from where `MainActivity` fetches its dependencies. We have a `TestMojoApplication` for screenshot tests, in which we mock/fake out the dependencies from `Injector`.

# From JSON to onDraw


# Screenshot Tests
Screenshot tests are in the `ScreenshotTests.kt` file.

Each test needs 3 inputs:

1. The test JSON layout
2. a PNG file of how the JSON layout on the screen renders
3. The size of the MojoView that we are testing, for example: 1024x1024
 
Inside the **androidTest/assets** folder, there are test JSON layouts and their corresponding rendered images(PNG format), for example:
the-layout-from-the-test.json
the-layout-from-the-test.png
The screenshot tests follow the steps below to perform a test:

1. The test sets up the `TestMojoApplication` with the test JSON layout.
2. The app renders the test JSON layout.
3. The test catches(screenshots) the `MojoView` by converting the `MojoView` to a bitmap.
4. The test compares the screenshot(Bitmap of the `MojoView`) with the test PNG(also a Bitmap) that we initially provided.

Below is a test example:
```
@Test
fun testTheRealLayoutFromTheTest() {
    val layoutFileName = "the-layout-from-the-test" // the JSON layout file name from "androidTest/assets/"
    application.layout = readLayoutFrom(context, layoutFileName) // sets the JSON layout on the app

    launch(Size(1024, 1024)) { // we can test different screen sizes
        onView(withId(R.id.mojoView))
            .check(matches(hasBitmap(context, layoutFileName))) // compares the MojoView Bitmap with the test PNG from "androidTest/assets/"
    }
}
```
# Unit Tests

