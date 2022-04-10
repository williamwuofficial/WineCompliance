import { observable } from "mobx";
import WineBreakdownPercentageEntity, { IWineBreakdownPercentageEntityAttributes } from "./WineBreakdownPercentageEntity";

export interface IWineBreakdownEntityAttributes {
    breakDownType : String;
    breakdown: Array<WineBreakdownPercentageEntity | IWineBreakdownPercentageEntityAttributes>;
}

export default class WineBreakdownEntity implements IWineBreakdownEntityAttributes {

    @observable
	public breakDownType : String;
    
    @observable    
    public breakdown: WineBreakdownPercentageEntity[] = [];

    constructor(attributes?: Partial<IWineBreakdownEntityAttributes>) {
        if (attributes) {
			if (attributes.breakDownType !== undefined) {
				this.breakDownType = attributes.breakDownType;
			}
            if (attributes.breakdown !== undefined && Array.isArray(attributes.breakdown)) {
				for (const entity of attributes.breakdown) {
					if (entity instanceof WineBreakdownPercentageEntity) {
						this.breakdown.push(entity);
					} else {
						this.breakdown.push(new WineBreakdownPercentageEntity(entity));
					}
				}
			}
        }
	}
}