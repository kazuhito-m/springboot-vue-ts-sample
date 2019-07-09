SELECT
    /*%expand*/*
FROM
    profiles.profile_images
WHERE
    user_id = /*userId*/1
;
