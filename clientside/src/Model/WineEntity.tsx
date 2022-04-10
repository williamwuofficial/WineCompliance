import { observable } from "mobx";
import WineBreakdownEntity from "./WineBreakdownEntity";

export interface IWineEntityAttributes {
    lotCode: string;
    volume: number;
    description: string;
    tankCode: string;
    productState: string;
    ownerName: string;
    yearBreakdown?: WineBreakdownEntity;
    varietyBreakdown?: WineBreakdownEntity;
    regionBreakdown?: WineBreakdownEntity;
    yearVarietyBreakdown?: WineBreakdownEntity;
}

export default class WineEntity implements IWineEntityAttributes {
    
    @observable
    public lotCode: string;

    @observable
    public volume: number;

    @observable
    public description: string;

    @observable
    public tankCode: string;

    @observable
    public productState: string;

    @observable
    public ownerName: string;

    @observable
    public yearBreakdown?: WineBreakdownEntity;

    @observable
    public varietyBreakdown?: WineBreakdownEntity;
    
    @observable    
    public regionBreakdown?: WineBreakdownEntity;

    @observable    
    public yearVarietyBreakdown?: WineBreakdownEntity;

    constructor(attributes?: Partial<IWineEntityAttributes>) {
        if (attributes) {
			if (attributes.lotCode !== undefined) {
				this.lotCode = attributes.lotCode;
			}
            if (attributes.volume !== undefined) {
				this.volume = attributes.volume;
			}
            if (attributes.description !== undefined) {
				this.description = attributes.description;
			}
            if (attributes.tankCode !== undefined) {
				this.tankCode = attributes.tankCode;
			}
            if (attributes.productState !== undefined) {
				this.productState = attributes.productState;
			}
            if (attributes.ownerName !== undefined) {
				this.ownerName = attributes.ownerName;
			}
        }
	}

    public setBreakdownPercentages(entity: WineBreakdownEntity) {
        switch(entity.breakDownType) {
            case "year":
                this.yearBreakdown = entity;
                break;
            case "region":
                this.regionBreakdown = entity;
                break;
            case "variety":
                this.varietyBreakdown = entity;
                break;
            case "year-variety":
                this.yearVarietyBreakdown = entity;
                break;
        }
    }

}