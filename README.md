[![Build Status](https://travis-ci.org/kmulvey/bootstrap-builder.png?branch=master)](https://travis-ci.org/kmulvey/bootstrap-builder)

bootstrap-builder
=================

Bootstrap Builder allows you to create override files in a subdirectory of bootstrap/less/ thus keeping the source files unchanges and easy to update from upstream.


## Prerequisites

* java 7: [openjdk](http://openjdk.java.net/install/) or [oracle](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
* [maven 3](https://maven.apache.org/download.cgi)


## Quick start

* Clone the repo: `git clone git@github.com:kmulvey/bootstrap-builder.git`.
* `mvn install`


## Override file format

The format of this file looks similar to a diff file and tweaking an actual diff is a good way to get started.

* **Remove a property**: line begins with a '-'
`- margin-left: 20px;`

* **Add a property**: line begins with a '+'
`+ background: #333;`

* **Remove a block**: selector line begins with a '-'.  There is no need to give the contents of the block as we are simply going to find that selector and remove the whole thing.
```
-.well-small {
}
```

* **Add a block**: selector line begins with a '+'.
```
+.well-large {
		padding: 24px;
		.border-radius(6px);
}
```

* When changing a nested selector be sure to correctly specify the parent selectors
```
.btn{
		&:focus {
-		.tab-focus();
+		outline: none;
		}
}
```

* A complete example
```
.well {
-	margin-bottom: 20px;
-	background-color: @wellBackground;
-	border: 1px solid darken(@well-bg, 7%);
+	border: 1px solid darken(@wellBackground, 70%);
		blockquote {
-		border-color: #ddd;
+		border-color: rgba(255, 255, 255);
		}
}
+.well-large {
		padding: 24px;
		.border-radius(6px);
}
-.well-small {
}
```
