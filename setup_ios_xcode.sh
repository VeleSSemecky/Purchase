#!/bin/bash

# Automated Xcode Project Generator for KMP iOS App
# This script creates a complete Xcode project structure

set -e

PROJECT_DIR="/Users/yuriimelnyk/StudioProjects/Purchase"
IOS_APP_DIR="$PROJECT_DIR/iosApp"
XCODE_PROJECT_DIR="$IOS_APP_DIR/iosApp.xcodeproj"
SHARED_FRAMEWORK_PATH="$PROJECT_DIR/shared/build/bin/iosSimulatorArm64/debugFramework/shared.framework"

echo "🚀 Creating Xcode project for Purchase iOS app..."

# Check if Xcode is installed
if ! command -v xcodebuild &> /dev/null; then
    echo "❌ Error: Xcode is not installed or xcodebuild is not in PATH"
    exit 1
fi

# Navigate to iosApp directory
cd "$IOS_APP_DIR"

# Remove old project if exists
if [ -d "$XCODE_PROJECT_DIR" ]; then
    echo "📦 Removing existing Xcode project..."
    rm -rf "$XCODE_PROJECT_DIR"
fi

# Create project directory structure
mkdir -p "$XCODE_PROJECT_DIR/project.xcworkspace"
mkdir -p "$XCODE_PROJECT_DIR/xcuserdata"

# Generate UUID for various project elements
PROJECT_UUID=$(uuidgen | tr '[:upper:]' '[:lower:]' | tr -d '-')
TARGET_UUID=$(uuidgen | tr '[:upper:]' '[:lower:]' | tr -d '-')
MAINGROUP_UUID=$(uuidgen | tr '[:upper:]' '[:lower:]' | tr -d '-')
SOURCES_UUID=$(uuidgen | tr '[:upper:]' '[:lower:]' | tr -d '-')
PRODUCTS_UUID=$(uuidgen | tr '[:upper:]' '[:lower:]' | tr -d '-')
BUILDCONFIG_DEBUG_UUID=$(uuidgen | tr '[:upper:]' '[:lower:]' | tr -d '-')
BUILDCONFIG_RELEASE_UUID=$(uuidgen | tr '[:upper:]' '[:lower:]' | tr -d '-')
CONFIGLIST_PROJECT_UUID=$(uuidgen | tr '[:upper:]' '[:lower:]' | tr -d '-')
CONFIGLIST_TARGET_UUID=$(uuidgen | tr '[:upper:]' '[:lower:]' | tr -d '-')
SOURCESBUILD_UUID=$(uuidgen | tr '[:upper:]' '[:lower:]' | tr -d '-')
FRAMEWORKSBUILD_UUID=$(uuidgen | tr '[:upper:]' '[:lower:]' | tr -d '-')
RESOURCESBUILD_UUID=$(uuidgen | tr '[:upper:]' '[:lower:]' | tr -d '-')
APP_PRODUCT_UUID=$(uuidgen | tr '[:upper:]' '[:lower:]' | tr -d '-')
CONTENTVIEW_UUID=$(uuidgen | tr '[:upper:]' '[:lower:]' | tr -d '-')
IOSAPP_UUID=$(uuidgen | tr '[:upper:]' '[:lower:]' | tr -d '-')
INFOPLIST_UUID=$(uuidgen | tr '[:upper:]' '[:lower:]' | tr -d '-')
RUNSCRIPT_UUID=$(uuidgen | tr '[:upper:]' '[:lower:]' | tr -d '-')

echo "📝 Generating project.pbxproj file..."

# Create the project.pbxproj file
cat > "$XCODE_PROJECT_DIR/project.pbxproj" << 'PBXPROJ_END'
// !$*UTF8*$!
{
	archiveVersion = 1;
	classes = {
	};
	objectVersion = 56;
	objects = {

/* Begin PBXBuildFile section */
		CONTENTVIEW_FILE_UUID /* ContentView.swift in Sources */ = {isa = PBXBuildFile; fileRef = CONTENTVIEW_UUID /* ContentView.swift */; };
		IOSAPP_FILE_UUID /* iOSApp.swift in Sources */ = {isa = PBXBuildFile; fileRef = IOSAPP_UUID /* iOSApp.swift */; };
/* End PBXBuildFile section */

/* Begin PBXFileReference section */
		APP_PRODUCT_UUID /* iosApp.app */ = {isa = PBXFileReference; explicitFileType = wrapper.application; includeInIndex = 0; path = iosApp.app; sourceTree = BUILT_PRODUCTS_DIR; };
		CONTENTVIEW_UUID /* ContentView.swift */ = {isa = PBXFileReference; lastKnownFileType = sourcecode.swift; path = ContentView.swift; sourceTree = "<group>"; };
		IOSAPP_UUID /* iOSApp.swift */ = {isa = PBXFileReference; lastKnownFileType = sourcecode.swift; path = iOSApp.swift; sourceTree = "<group>"; };
		INFOPLIST_UUID /* Info.plist */ = {isa = PBXFileReference; lastKnownFileType = text.plist.xml; path = Info.plist; sourceTree = "<group>"; };
/* End PBXFileReference section */

/* Begin PBXFrameworksBuildPhase section */
		FRAMEWORKSBUILD_UUID /* Frameworks */ = {
			isa = PBXFrameworksBuildPhase;
			buildActionMask = 2147483647;
			files = (
			);
			runOnlyForDeploymentPostprocessing = 0;
		};
/* End PBXFrameworksBuildPhase section */

/* Begin PBXGroup section */
		MAINGROUP_UUID = {
			isa = PBXGroup;
			children = (
				SOURCES_UUID /* iosApp */,
				PRODUCTS_UUID /* Products */,
			);
			sourceTree = "<group>";
		};
		PRODUCTS_UUID /* Products */ = {
			isa = PBXGroup;
			children = (
				APP_PRODUCT_UUID /* iosApp.app */,
			);
			name = Products;
			sourceTree = "<group>";
		};
		SOURCES_UUID /* iosApp */ = {
			isa = PBXGroup;
			children = (
				IOSAPP_UUID /* iOSApp.swift */,
				CONTENTVIEW_UUID /* ContentView.swift */,
				INFOPLIST_UUID /* Info.plist */,
			);
			path = iosApp;
			sourceTree = "<group>";
		};
/* End PBXGroup section */

/* Begin PBXNativeTarget section */
		TARGET_UUID /* iosApp */ = {
			isa = PBXNativeTarget;
			buildConfigurationList = CONFIGLIST_TARGET_UUID /* Build configuration list for PBXNativeTarget "iosApp" */;
			buildPhases = (
				RUNSCRIPT_UUID /* Build Shared Framework */,
				SOURCESBUILD_UUID /* Sources */,
				FRAMEWORKSBUILD_UUID /* Frameworks */,
				RESOURCESBUILD_UUID /* Resources */,
			);
			buildRules = (
			);
			dependencies = (
			);
			name = iosApp;
			productName = iosApp;
			productReference = APP_PRODUCT_UUID /* iosApp.app */;
			productType = "com.apple.product-type.application";
		};
/* End PBXNativeTarget section */

/* Begin PBXProject section */
		PROJECT_UUID /* Project object */ = {
			isa = PBXProject;
			attributes = {
				BuildIndependentTargetsInParallel = 1;
				LastSwiftUpdateCheck = 1600;
				LastUpgradeCheck = 1600;
				TargetAttributes = {
					TARGET_UUID = {
						CreatedOnToolsVersion = 16.0;
					};
				};
			};
			buildConfigurationList = CONFIGLIST_PROJECT_UUID /* Build configuration list for PBXProject "iosApp" */;
			compatibilityVersion = "Xcode 14.0";
			developmentRegion = en;
			hasScannedForEncodings = 0;
			knownRegions = (
				en,
				Base,
			);
			mainGroup = MAINGROUP_UUID;
			productRefGroup = PRODUCTS_UUID /* Products */;
			projectDirPath = "";
			projectRoot = "";
			targets = (
				TARGET_UUID /* iosApp */,
			);
		};
/* End PBXProject section */

/* Begin PBXResourcesBuildPhase section */
		RESOURCESBUILD_UUID /* Resources */ = {
			isa = PBXResourcesBuildPhase;
			buildActionMask = 2147483647;
			files = (
			);
			runOnlyForDeploymentPostprocessing = 0;
		};
/* End PBXResourcesBuildPhase section */

/* Begin PBXShellScriptBuildPhase section */
		RUNSCRIPT_UUID /* Build Shared Framework */ = {
			isa = PBXShellScriptBuildPhase;
			buildActionMask = 2147483647;
			files = (
			);
			inputFileListPaths = (
			);
			inputPaths = (
			);
			name = "Build Shared Framework";
			outputFileListPaths = (
			);
			outputPaths = (
			);
			runOnlyForDeploymentPostprocessing = 0;
			shellPath = /bin/sh;
			shellScript = "cd \"$SRCROOT/..\"\n./gradlew :shared:linkDebugFrameworkIosSimulatorArm64\n";
		};
/* End PBXShellScriptBuildPhase section */

/* Begin PBXSourcesBuildPhase section */
		SOURCESBUILD_UUID /* Sources */ = {
			isa = PBXSourcesBuildPhase;
			buildActionMask = 2147483647;
			files = (
				CONTENTVIEW_FILE_UUID /* ContentView.swift in Sources */,
				IOSAPP_FILE_UUID /* iOSApp.swift in Sources */,
			);
			runOnlyForDeploymentPostprocessing = 0;
		};
/* End PBXSourcesBuildPhase section */

/* Begin XCBuildConfiguration section */
		BUILDCONFIG_DEBUG_UUID /* Debug */ = {
			isa = XCBuildConfiguration;
			buildSettings = {
				ALWAYS_SEARCH_USER_PATHS = NO;
				ASSETCATALOG_COMPILER_GENERATE_SWIFT_ASSET_SYMBOL_EXTENSIONS = YES;
				CLANG_ANALYZER_NONNULL = YES;
				CLANG_ANALYZER_NUMBER_OBJECT_CONVERSION = YES_AGGRESSIVE;
				CLANG_CXX_LANGUAGE_STANDARD = "gnu++20";
				CLANG_ENABLE_MODULES = YES;
				CLANG_ENABLE_OBJC_ARC = YES;
				CLANG_ENABLE_OBJC_WEAK = YES;
				CLANG_WARN_BLOCK_CAPTURE_AUTORELEASING = YES;
				CLANG_WARN_BOOL_CONVERSION = YES;
				CLANG_WARN_COMMA = YES;
				CLANG_WARN_CONSTANT_CONVERSION = YES;
				CLANG_WARN_DEPRECATED_OBJC_IMPLEMENTATIONS = YES;
				CLANG_WARN_DIRECT_OBJC_ISA_USAGE = YES_ERROR;
				CLANG_WARN_DOCUMENTATION_COMMENTS = YES;
				CLANG_WARN_EMPTY_BODY = YES;
				CLANG_WARN_ENUM_CONVERSION = YES;
				CLANG_WARN_INFINITE_RECURSION = YES;
				CLANG_WARN_INT_CONVERSION = YES;
				CLANG_WARN_NON_LITERAL_NULL_CONVERSION = YES;
				CLANG_WARN_OBJC_IMPLICIT_RETAIN_SELF = YES;
				CLANG_WARN_OBJC_LITERAL_CONVERSION = YES;
				CLANG_WARN_OBJC_ROOT_CLASS = YES_ERROR;
				CLANG_WARN_QUOTED_INCLUDE_IN_FRAMEWORK_HEADER = YES;
				CLANG_WARN_RANGE_LOOP_ANALYSIS = YES;
				CLANG_WARN_STRICT_PROTOTYPES = YES;
				CLANG_WARN_SUSPICIOUS_MOVE = YES;
				CLANG_WARN_UNGUARDED_AVAILABILITY = YES_AGGRESSIVE;
				CLANG_WARN_UNREACHABLE_CODE = YES;
				CLANG_WARN__DUPLICATE_METHOD_MATCH = YES;
				COPY_PHASE_STRIP = NO;
				DEBUG_INFORMATION_FORMAT = dwarf;
				ENABLE_STRICT_OBJC_MSGSEND = YES;
				ENABLE_TESTABILITY = YES;
				ENABLE_USER_SCRIPT_SANDBOXING = NO;
				GCC_C_LANGUAGE_STANDARD = gnu17;
				GCC_DYNAMIC_NO_PIC = NO;
				GCC_NO_COMMON_BLOCKS = YES;
				GCC_OPTIMIZATION_LEVEL = 0;
				GCC_PREPROCESSOR_DEFINITIONS = (
					"DEBUG=1",
					"$(inherited)",
				);
				GCC_WARN_64_TO_32_BIT_CONVERSION = YES;
				GCC_WARN_ABOUT_RETURN_TYPE = YES_ERROR;
				GCC_WARN_UNDECLARED_SELECTOR = YES;
				GCC_WARN_UNINITIALIZED_AUTOS = YES_AGGRESSIVE;
				GCC_WARN_UNUSED_FUNCTION = YES;
				GCC_WARN_UNUSED_VARIABLE = YES;
				IPHONEOS_DEPLOYMENT_TARGET = 15.0;
				LOCALIZATION_PREFERS_STRING_CATALOGS = YES;
				MTL_ENABLE_DEBUG_INFO = INCLUDE_SOURCE;
				MTL_FAST_MATH = YES;
				ONLY_ACTIVE_ARCH = YES;
				SDKROOT = iphoneos;
				SWIFT_ACTIVE_COMPILATION_CONDITIONS = "DEBUG $(inherited)";
				SWIFT_OPTIMIZATION_LEVEL = "-Onone";
			};
			name = Debug;
		};
		BUILDCONFIG_RELEASE_UUID /* Release */ = {
			isa = XCBuildConfiguration;
			buildSettings = {
				ALWAYS_SEARCH_USER_PATHS = NO;
				ASSETCATALOG_COMPILER_GENERATE_SWIFT_ASSET_SYMBOL_EXTENSIONS = YES;
				CLANG_ANALYZER_NONNULL = YES;
				CLANG_ANALYZER_NUMBER_OBJECT_CONVERSION = YES_AGGRESSIVE;
				CLANG_CXX_LANGUAGE_STANDARD = "gnu++20";
				CLANG_ENABLE_MODULES = YES;
				CLANG_ENABLE_OBJC_ARC = YES;
				CLANG_ENABLE_OBJC_WEAK = YES;
				CLANG_WARN_BLOCK_CAPTURE_AUTORELEASING = YES;
				CLANG_WARN_BOOL_CONVERSION = YES;
				CLANG_WARN_COMMA = YES;
				CLANG_WARN_CONSTANT_CONVERSION = YES;
				CLANG_WARN_DEPRECATED_OBJC_IMPLEMENTATIONS = YES;
				CLANG_WARN_DIRECT_OBJC_ISA_USAGE = YES_ERROR;
				CLANG_WARN_DOCUMENTATION_COMMENTS = YES;
				CLANG_WARN_EMPTY_BODY = YES;
				CLANG_WARN_ENUM_CONVERSION = YES;
				CLANG_WARN_INFINITE_RECURSION = YES;
				CLANG_WARN_INT_CONVERSION = YES;
				CLANG_WARN_NON_LITERAL_NULL_CONVERSION = YES;
				CLANG_WARN_OBJC_IMPLICIT_RETAIN_SELF = YES;
				CLANG_WARN_OBJC_LITERAL_CONVERSION = YES;
				CLANG_WARN_OBJC_ROOT_CLASS = YES_ERROR;
				CLANG_WARN_QUOTED_INCLUDE_IN_FRAMEWORK_HEADER = YES;
				CLANG_WARN_RANGE_LOOP_ANALYSIS = YES;
				CLANG_WARN_STRICT_PROTOTYPES = YES;
				CLANG_WARN_SUSPICIOUS_MOVE = YES;
				CLANG_WARN_UNGUARDED_AVAILABILITY = YES_AGGRESSIVE;
				CLANG_WARN_UNREACHABLE_CODE = YES;
				CLANG_WARN__DUPLICATE_METHOD_MATCH = YES;
				COPY_PHASE_STRIP = NO;
				DEBUG_INFORMATION_FORMAT = "dwarf-with-dsym";
				ENABLE_NS_ASSERTIONS = NO;
				ENABLE_STRICT_OBJC_MSGSEND = YES;
				ENABLE_USER_SCRIPT_SANDBOXING = NO;
				GCC_C_LANGUAGE_STANDARD = gnu17;
				GCC_NO_COMMON_BLOCKS = YES;
				GCC_WARN_64_TO_32_BIT_CONVERSION = YES;
				GCC_WARN_ABOUT_RETURN_TYPE = YES_ERROR;
				GCC_WARN_UNDECLARED_SELECTOR = YES;
				GCC_WARN_UNINITIALIZED_AUTOS = YES_AGGRESSIVE;
				GCC_WARN_UNUSED_FUNCTION = YES;
				GCC_WARN_UNUSED_VARIABLE = YES;
				IPHONEOS_DEPLOYMENT_TARGET = 15.0;
				LOCALIZATION_PREFERS_STRING_CATALOGS = YES;
				MTL_ENABLE_DEBUG_INFO = NO;
				MTL_FAST_MATH = YES;
				SDKROOT = iphoneos;
				SWIFT_COMPILATION_MODE = wholemodule;
				VALIDATE_PRODUCT = YES;
			};
			name = Release;
		};
		BUILDCONFIG_TARGET_DEBUG_UUID /* Debug */ = {
			isa = XCBuildConfiguration;
			buildSettings = {
				ASSETCATALOG_COMPILER_APPICON_NAME = AppIcon;
				ASSETCATALOG_COMPILER_GLOBAL_ACCENT_COLOR_NAME = AccentColor;
				CODE_SIGN_STYLE = Automatic;
				CURRENT_PROJECT_VERSION = 1;
				DEVELOPMENT_TEAM = "";
				ENABLE_PREVIEWS = YES;
				FRAMEWORK_SEARCH_PATHS = (
					"$(inherited)",
					"$(SRCROOT)/../shared/build/bin/iosSimulatorArm64/debugFramework",
				);
				GENERATE_INFOPLIST_FILE = NO;
				INFOPLIST_FILE = iosApp/Info.plist;
				INFOPLIST_KEY_UIApplicationSceneManifest_Generation = YES;
				INFOPLIST_KEY_UIApplicationSupportsIndirectInputEvents = YES;
				INFOPLIST_KEY_UILaunchScreen_Generation = YES;
				INFOPLIST_KEY_UISupportedInterfaceOrientations = "UIInterfaceOrientationPortrait UIInterfaceOrientationLandscapeLeft UIInterfaceOrientationLandscapeRight";
				INFOPLIST_KEY_UISupportedInterfaceOrientations_iPad = "UIInterfaceOrientationPortrait UIInterfaceOrientationPortraitUpsideDown UIInterfaceOrientationLandscapeLeft UIInterfaceOrientationLandscapeRight";
				IPHONEOS_DEPLOYMENT_TARGET = 15.0;
				LD_RUNPATH_SEARCH_PATHS = (
					"$(inherited)",
					"@executable_path/Frameworks",
				);
				MARKETING_VERSION = 1.0;
				OTHER_LDFLAGS = (
					"$(inherited)",
					"-framework",
					shared,
				);
				PRODUCT_BUNDLE_IDENTIFIER = com.veles.purchase.iosApp;
				PRODUCT_NAME = "$(TARGET_NAME)";
				SWIFT_EMIT_LOC_STRINGS = YES;
				SWIFT_VERSION = 5.0;
				TARGETED_DEVICE_FAMILY = "1,2";
			};
			name = Debug;
		};
		BUILDCONFIG_TARGET_RELEASE_UUID /* Release */ = {
			isa = XCBuildConfiguration;
			buildSettings = {
				ASSETCATALOG_COMPILER_APPICON_NAME = AppIcon;
				ASSETCATALOG_COMPILER_GLOBAL_ACCENT_COLOR_NAME = AccentColor;
				CODE_SIGN_STYLE = Automatic;
				CURRENT_PROJECT_VERSION = 1;
				DEVELOPMENT_TEAM = "";
				ENABLE_PREVIEWS = YES;
				FRAMEWORK_SEARCH_PATHS = (
					"$(inherited)",
					"$(SRCROOT)/../shared/build/bin/iosSimulatorArm64/debugFramework",
				);
				GENERATE_INFOPLIST_FILE = NO;
				INFOPLIST_FILE = iosApp/Info.plist;
				INFOPLIST_KEY_UIApplicationSceneManifest_Generation = YES;
				INFOPLIST_KEY_UIApplicationSupportsIndirectInputEvents = YES;
				INFOPLIST_KEY_UILaunchScreen_Generation = YES;
				INFOPLIST_KEY_UISupportedInterfaceOrientations = "UIInterfaceOrientationPortrait UIInterfaceOrientationLandscapeLeft UIInterfaceOrientationLandscapeRight";
				INFOPLIST_KEY_UISupportedInterfaceOrientations_iPad = "UIInterfaceOrientationPortrait UIInterfaceOrientationPortraitUpsideDown UIInterfaceOrientationLandscapeLeft UIInterfaceOrientationLandscapeRight";
				IPHONEOS_DEPLOYMENT_TARGET = 15.0;
				LD_RUNPATH_SEARCH_PATHS = (
					"$(inherited)",
					"@executable_path/Frameworks",
				);
				MARKETING_VERSION = 1.0;
				OTHER_LDFLAGS = (
					"$(inherited)",
					"-framework",
					shared,
				);
				PRODUCT_BUNDLE_IDENTIFIER = com.veles.purchase.iosApp;
				PRODUCT_NAME = "$(TARGET_NAME)";
				SWIFT_EMIT_LOC_STRINGS = YES;
				SWIFT_VERSION = 5.0;
				TARGETED_DEVICE_FAMILY = "1,2";
			};
			name = Release;
		};
/* End XCBuildConfiguration section */

/* Begin XCConfigurationList section */
		CONFIGLIST_PROJECT_UUID /* Build configuration list for PBXProject "iosApp" */ = {
			isa = XCConfigurationList;
			buildConfigurations = (
				BUILDCONFIG_DEBUG_UUID /* Debug */,
				BUILDCONFIG_RELEASE_UUID /* Release */,
			);
			defaultConfigurationIsVisible = 0;
			defaultConfigurationName = Release;
		};
		CONFIGLIST_TARGET_UUID /* Build configuration list for PBXNativeTarget "iosApp" */ = {
			isa = XCConfigurationList;
			buildConfigurations = (
				BUILDCONFIG_TARGET_DEBUG_UUID /* Debug */,
				BUILDCONFIG_TARGET_RELEASE_UUID /* Release */,
			);
			defaultConfigurationIsVisible = 0;
			defaultConfigurationName = Release;
		};
/* End XCConfigurationList section */
	};
	rootObject = PROJECT_UUID /* Project object */;
}
PBXPROJ_END

# Now replace all the UUIDs with actual generated UUIDs
sed -i '' "s/PROJECT_UUID/$PROJECT_UUID/g" "$XCODE_PROJECT_DIR/project.pbxproj"
sed -i '' "s/TARGET_UUID/$TARGET_UUID/g" "$XCODE_PROJECT_DIR/project.pbxproj"
sed -i '' "s/MAINGROUP_UUID/$MAINGROUP_UUID/g" "$XCODE_PROJECT_DIR/project.pbxproj"
sed -i '' "s/SOURCES_UUID/$SOURCES_UUID/g" "$XCODE_PROJECT_DIR/project.pbxproj"
sed -i '' "s/PRODUCTS_UUID/$PRODUCTS_UUID/g" "$XCODE_PROJECT_DIR/project.pbxproj"
sed -i '' "s/BUILDCONFIG_DEBUG_UUID/$BUILDCONFIG_DEBUG_UUID/g" "$XCODE_PROJECT_DIR/project.pbxproj"
sed -i '' "s/BUILDCONFIG_RELEASE_UUID/$BUILDCONFIG_RELEASE_UUID/g" "$XCODE_PROJECT_DIR/project.pbxproj"
sed -i '' "s/BUILDCONFIG_TARGET_DEBUG_UUID/$(uuidgen | tr '[:upper:]' '[:lower:]' | tr -d '-')/g" "$XCODE_PROJECT_DIR/project.pbxproj"
sed -i '' "s/BUILDCONFIG_TARGET_RELEASE_UUID/$(uuidgen | tr '[:upper:]' '[:lower:]' | tr -d '-')/g" "$XCODE_PROJECT_DIR/project.pbxproj"
sed -i '' "s/CONFIGLIST_PROJECT_UUID/$CONFIGLIST_PROJECT_UUID/g" "$XCODE_PROJECT_DIR/project.pbxproj"
sed -i '' "s/CONFIGLIST_TARGET_UUID/$CONFIGLIST_TARGET_UUID/g" "$XCODE_PROJECT_DIR/project.pbxproj"
sed -i '' "s/SOURCESBUILD_UUID/$SOURCESBUILD_UUID/g" "$XCODE_PROJECT_DIR/project.pbxproj"
sed -i '' "s/FRAMEWORKSBUILD_UUID/$FRAMEWORKSBUILD_UUID/g" "$XCODE_PROJECT_DIR/project.pbxproj"
sed -i '' "s/RESOURCESBUILD_UUID/$RESOURCESBUILD_UUID/g" "$XCODE_PROJECT_DIR/project.pbxproj"
sed -i '' "s/APP_PRODUCT_UUID/$APP_PRODUCT_UUID/g" "$XCODE_PROJECT_DIR/project.pbxproj"
sed -i '' "s/CONTENTVIEW_UUID/$CONTENTVIEW_UUID/g" "$XCODE_PROJECT_DIR/project.pbxproj"
sed -i '' "s/CONTENTVIEW_FILE_UUID/$(uuidgen | tr '[:upper:]' '[:lower:]' | tr -d '-')/g" "$XCODE_PROJECT_DIR/project.pbxproj"
sed -i '' "s/IOSAPP_UUID/$IOSAPP_UUID/g" "$XCODE_PROJECT_DIR/project.pbxproj"
sed -i '' "s/IOSAPP_FILE_UUID/$(uuidgen | tr '[:upper:]' '[:lower:]' | tr -d '-')/g" "$XCODE_PROJECT_DIR/project.pbxproj"
sed -i '' "s/INFOPLIST_UUID/$INFOPLIST_UUID/g" "$XCODE_PROJECT_DIR/project.pbxproj"
sed -i '' "s/RUNSCRIPT_UUID/$RUNSCRIPT_UUID/g" "$XCODE_PROJECT_DIR/project.pbxproj"

echo "📝 Creating workspace settings..."

# Create workspace contents
cat > "$XCODE_PROJECT_DIR/project.xcworkspace/contents.xcworkspacedata" << 'WORKSPACE_END'
<?xml version="1.0" encoding="UTF-8"?>
<Workspace
   version = "1.0">
   <FileRef
      location = "self:">
   </FileRef>
</Workspace>
WORKSPACE_END

# Create workspace shared settings
mkdir -p "$XCODE_PROJECT_DIR/project.xcworkspace/xcshareddata"
cat > "$XCODE_PROJECT_DIR/project.xcworkspace/xcshareddata/IDEWorkspaceChecks.plist" << 'CHECKS_END'
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE plist PUBLIC "-//Apple//DTD PLIST 1.0//EN" "http://www.apple.com/DTDs/PropertyList-1.0.dtd">
<plist version="1.0">
<dict>
	<key>IDEDidComputeMac32BitWarning</key>
	<true/>
</dict>
</plist>
CHECKS_END

echo ""
echo "✅ Xcode project created successfully!"
echo ""
echo "📂 Project location: $XCODE_PROJECT_DIR"
echo ""
echo "🚀 Next steps:"
echo "1. Open the project: open $XCODE_PROJECT_DIR"
echo "2. Select a simulator (e.g., iPhone 15)"
echo "3. Build and run (⌘R)"
echo ""
echo "📝 Note: The project is configured to:"
echo "   - Build the shared framework automatically before each run"
echo "   - Link against the shared.framework"
echo "   - Use SwiftUI for the UI"
echo "   - Use Compose Multiplatform from the shared module"
echo ""

