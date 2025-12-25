#!/bin/bash

# Script to copy Compose Multiplatform resources from framework to app bundle
# Add this as a "Run Script" build phase in Xcode AFTER "Build Shared Framework"

echo "================================================"
echo "📦 Copy Compose Resources Script"
echo "================================================"

# Check if running in Xcode environment
if [ -z "$BUILT_PRODUCTS_DIR" ]; then
    echo "⚠️  Not running in Xcode environment - skipping"
    echo "   (This is normal when testing the script manually)"
    exit 0
fi

echo "Build Products Dir: $BUILT_PRODUCTS_DIR"
echo "Configuration: $CONFIGURATION"
echo "Platform: $PLATFORM_NAME"

# Try multiple possible framework locations
FRAMEWORK_LOCATIONS=(
    "${BUILT_PRODUCTS_DIR}/shared.framework"
    "${BUILT_PRODUCTS_DIR}/Debug/shared.framework"
    "${BUILT_PRODUCTS_DIR}/Debug-iphonesimulator/shared.framework"
    "${BUILT_PRODUCTS_DIR}/${CONFIGURATION}/shared.framework"
    "${BUILT_PRODUCTS_DIR}/${CONFIGURATION}-${PLATFORM_NAME}/shared.framework"
)

FRAMEWORK_PATH=""
for location in "${FRAMEWORK_LOCATIONS[@]}"; do
    if [ -d "$location" ]; then
        FRAMEWORK_PATH="$location"
        echo "✅ Found framework at: $FRAMEWORK_PATH"
        break
    fi
done

if [ -z "$FRAMEWORK_PATH" ]; then
    echo "❌ Error: Could not find shared.framework in any expected location:"
    for location in "${FRAMEWORK_LOCATIONS[@]}"; do
        echo "   - $location"
    done
    echo ""
    echo "💡 Tip: Make sure 'Build Shared Framework' phase runs first"
    exit 1
fi

RESOURCES_SRC="${FRAMEWORK_PATH}/compose-resources"

# Determine destination
if [ -n "$CONTENTS_FOLDER_PATH" ]; then
    RESOURCES_DEST="${BUILT_PRODUCTS_DIR}/${CONTENTS_FOLDER_PATH}/compose-resources"
else
    # Fallback for iOS
    RESOURCES_DEST="${BUILT_PRODUCTS_DIR}/${PRODUCT_NAME}.app/compose-resources"
fi

echo "Source: $RESOURCES_SRC"
echo "Destination: $RESOURCES_DEST"

if [ ! -d "$RESOURCES_SRC" ]; then
    echo "⚠️  Warning: No compose-resources found in framework"
    echo "   Expected at: $RESOURCES_SRC"
    echo ""
    echo "💡 This may cause MissingResourceException at runtime!"
    echo "   To fix, run: ./gradlew :shared:linkDebugFrameworkIosSimulatorArm64"
    echo ""
    echo "⚠️  Continuing build without copying resources..."
    exit 0
fi

echo "📦 Copying resources..."

# Create destination directory if needed
mkdir -p "$(dirname "$RESOURCES_DEST")"

# Remove old resources to avoid conflicts
if [ -d "$RESOURCES_DEST" ]; then
    echo "   Removing old resources..."
    rm -rf "$RESOURCES_DEST"
fi

# Copy resources
if cp -R "$RESOURCES_SRC" "$RESOURCES_DEST"; then
    echo "✅ Compose resources copied successfully!"
    echo ""

    # Show what was copied
    if [ -d "$RESOURCES_DEST" ]; then
        RESOURCE_COUNT=$(find "$RESOURCES_DEST" -type f | wc -l | tr -d ' ')
        echo "   Copied $RESOURCE_COUNT resource files"
    fi
else
    echo "❌ Error: Failed to copy resources"
    exit 1
fi

echo "================================================"

