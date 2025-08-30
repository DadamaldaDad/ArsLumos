# Disclaimer: Most of this code is AI generated

from PIL import Image
from pathlib import Path
import os
import shutil

INPUT_DIR = "input"
OUTPUT_DIR = "output"

DEFAULT_DIR = Path("output/default").resolve()
REFRESH_DIR = Path("output/refresh").resolve()

excluded = "output/default/ars_additions/textures/item/ritual_chunk_loading.png"

output_paths = []
output_paths_refresh = []

def hex_to_rgb(hex_color: str) -> tuple[int, int, int]:
    hex_color = hex_color.lstrip("#")  # remove leading "#"
    return tuple(int(hex_color[i:i+2], 16) for i in (0, 2, 4))

hex_colors = ["#858599", "#595c6b", "#3f4357", "#323543", "#b5b6c4", "#a2a3b4",
              "#a2a18f", "#9e9c8a", "#a5a492", "#a09f8c", "#a5a391", "#a8a795", "#a4a290"]

# Colors to make transparent (exact RGB values)
TRANSPARENT_COLORS = [hex_to_rgb(color) for color in hex_colors]

for root, _, files in os.walk(INPUT_DIR):
    for file in files:
        # Full input path
        input_path = os.path.join(root, file)

        # Relative path
        rel_path = os.path.relpath(input_path, INPUT_DIR)
            
        if not file.endswith(".png"):
            if(file.endswith(".png.mcmeta")):
                # Output path with "_e" before extension
                name = rel_path[:-11]
                output_rel_path = f"{name}_e.png.mcmeta"
                output_path = os.path.join(OUTPUT_DIR, output_rel_path)

                shutil.copyfile(input_path, output_path)

            continue

        # Output path with "_e" before extension
        name, ext = os.path.splitext(rel_path)
        output_rel_path = f"{name}_e{ext}"
        output_path = os.path.join(OUTPUT_DIR, output_rel_path)

        # Make sure subdirectories exist
        os.makedirs(os.path.dirname(output_path), exist_ok=True)

        # Load + process image
        img = Image.open(input_path).convert("RGBA")
        pixels = img.load()

        for y in range(img.height):
            for x in range(img.width):
                r, g, b, a = pixels[x, y]
                if (r, g, b) in TRANSPARENT_COLORS or (r == g and g == b):
                    pixels[x, y] = (r, g, b, 0)  # transparent

        img.save(output_path)
        print(f"Processed {rel_path}")

        file_path = Path(output_path).resolve()

        if(DEFAULT_DIR in file_path.parents and output_path != excluded):
            output_paths.append(f"ResourceLocation.parse(\"{output_rel_path.split("/")[1]}:{"/".join(output_rel_path.split("/")[2:])[:-4]}\")")
        if(REFRESH_DIR in file_path.parents):
            output_paths_refresh.append(f"ResourceLocation.parse(\"{output_rel_path.split("/")[1]}:{"/".join(output_rel_path.split("/")[2:])[:-4]}\")")

print(f"\tprivate static final List<ResourceLocation> RITUAL_TABLETS = List.of(\n\t\t\t{",\n\t\t\t".join(output_paths)}\n\t);")
print(f"\n\tprivate static final List<ResourceLocation> RITUAL_TABLETS_REFRESH = List.of(\n\t\t\t{",\n\t\t\t".join(output_paths_refresh)}\n\t);")
