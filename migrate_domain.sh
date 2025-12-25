#!/bin/bash

# Phase 5 - Task 5.3: Domain Module Migration Script
# Copies domain files from domain module to shared/commonMain and makes KMP adjustments

set -e

PROJECT_ROOT="/Users/yuriimelnyk/StudioProjects/Purchase"
SOURCE_DIR="$PROJECT_ROOT/domain/src/main/java/com/veles/purchase/domain"
DEST_DIR="$PROJECT_ROOT/shared/src/commonMain/kotlin/com/veles/purchase/domain"

echo "🚀 Starting Domain Module Migration..."
echo "Source: $SOURCE_DIR"
echo "Destination: $DEST_DIR"
echo ""

# Create destination directories
echo "📁 Creating directory structure..."
mkdir -p "$DEST_DIR/model"
mkdir -p "$DEST_DIR/repository"
mkdir -p "$DEST_DIR/utill"

# Copy all domain files
echo "📋 Copying domain files..."
cp -R "$SOURCE_DIR/"* "$DEST_DIR/"

echo "✅ Files copied successfully!"
echo ""
echo "📝 Manual fixes needed:"
echo "1. Replace java.util.Calendar with kotlinx.datetime"
echo "2. Remove javax.inject imports"
echo "3. Replace LiveData with Flow (if any)"
echo "4. Remove @Parcelize annotations (if any)"
echo "5. Add @Serializable where needed"
echo ""
echo "Total files copied: $(find $DEST_DIR -name '*.kt' | wc -l)"
echo ""
echo "Next steps:"
echo "1. Review copied files"
echo "2. Run: ./gradlew :shared:build"
echo "3. Fix any compilation errors"
echo ""
echo "✨ Domain migration script complete!"

