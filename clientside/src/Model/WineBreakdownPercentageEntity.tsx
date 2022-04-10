import { observable } from "mobx";

export interface IWineBreakdownPercentageEntityAttributes {
    key : String;
    percentage : Number;
}

export default class WineBreakdownPercentageEntity implements IWineBreakdownPercentageEntityAttributes {

    @observable
	public key : String;
    
    @observable
    public percentage : Number;

    constructor(attributes?: Partial<IWineBreakdownPercentageEntityAttributes>) {
        if (attributes) {
			if (attributes.key !== undefined) {
				this.key = attributes.key;
			}
            if (attributes.percentage !== undefined) {
				this.percentage = attributes.percentage;
			}
        }
	}
}