import * as fs from 'fs';
import * as path from 'path';
import * as dotenv from 'dotenv';

export  function  getBaseUrl(): string {
      const envKey = `ARAVIND_BASE_URL_UI`;

        try {
            const envFilePath = path.resolve(process.cwd(),'.env'); 

            console.log("Looking for .env file at:", envFilePath);
            
            if (fs.existsSync(envFilePath)) {
                const parsedEnv = dotenv.parse(fs.readFileSync(envFilePath));
                
                if (parsedEnv[envKey]) {
                    return parsedEnv[envKey];
                }
            }
        } catch (err) {
            console.warn(`Could not read or parse the .env file. Falling back to process.env...`);
        }


        const base_url = process.env[envKey];
        
        if (!base_url) {
            throw new Error(`Missing secret: ${envKey} is not defined.`);
        }

        return base_url;
    }
