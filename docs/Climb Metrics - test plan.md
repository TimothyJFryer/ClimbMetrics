# Phase 2:

## Registration:

### Tests:

- REG-1 Users can register with a valid email and password  
- REG-2 Users can't register the same email twice  
- REG-3 Users can't register with no password  
- REG-4 Users can't register with no email  
- REG-5 Users can't register with invalid password

## Login:

### Tests:

- LOG-1 Users can log in with correct email and password  
- LOG-2 Users can't log in with incorrect password  
- LOG-3 Users can't log in with invalid email  
- LOG-4 Users can't log in with an unregistered email  
- LOG-5 Users can log out

## Authorisation & Access Control 

### Tests:

- AUTH-1 Authenticated users can view personal (customised) profile page  
- AUTH-2 Unauthenticated users can't view profile pages  
- AUTH-3 Users cannot modify another users data  
- AUTH-4 Users can modify their own data

# Phase 3:

## Climb logging:

### Tests:

- CLOG-1 \- Users can create valid climb log  
- CLOG-2 Users cannot create invalid climb log  
- CLOG-3 Users can edit their climbs  
- CLOG-4 Users can’t edit other climbs  
- CLOG-5 Users can add notes  
- CLOG-6 Users can increase attempts  
- CLOG-7 Users can decrease attempts  
- CLOG-8 Users can delete their climbs  
- CLOG-9 Users can add climb info

# Phase 4:

## Video Upload:

### Tests:

- VID-1 Users can upload valid video files  
- VID-2 Users cannot upload files of invalid type  
- VID-3 Users cannot upload files too large  
- VID-4 Users can playback their videos  
- VID-5 unauthenticated users cannot access videos  
- VID-6 Users can associate a video to an attempts

# Phase 5:

## Progress statistics

### Tests:

- STAT-1 Users can view their own progress stats  
- STAT-2 Stats are calculated correctly from known set  
- STAT-3 unauthenticated users cannot see stats

